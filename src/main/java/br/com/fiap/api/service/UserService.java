package br.com.fiap.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.model.User;
import br.com.fiap.api.repository.UserRepository;
import jakarta.validation.Valid;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade {@link User}.
 * 
 * <p>Inclui operações CRUD (Create, Read, Update, Delete), autenticação de usuários
 * e redefinição de senhas.</p>
 * 
 * <p>Este serviço utiliza {@link UserRepository} para persistência e {@link PasswordEncoder}
 * para manipulação segura de senhas.</p>
 * 
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------------------------- CRUD ---------------------------------

    /**
     * Lista todos os usuários cadastrados.
     * 
     * @return lista contendo todos os usuários
     */
    public List<User> listAll() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo seu ID.
     * 
     * @param id identificador do usuário
     * @return um {@link Optional} com o usuário, se encontrado
     */
    public Optional<User> searchForId(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Salva um novo usuário, codificando a senha antes de persistir.
     * 
     * @param user objeto {@link User} a ser salvo
     * @return o usuário salvo com ID gerado
     */
    public User save(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }
    
    /**
     * Atualiza um usuário existente com os dados fornecidos.
     * 
     * @param id identificador do usuário a ser atualizado
     * @param updatedUser objeto {@link User} contendo os dados atualizados
     * @return o usuário atualizado
     * @throws RuntimeException se o usuário não for encontrado
     */
    public User update(Long id, @Valid User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setClientName(updatedUser.getClientName());
            user.setEmail(updatedUser.getEmail());
            user.setBetMaxValue(updatedUser.getBetMaxValue());
            user.setUserPixKey(updatedUser.getUserPixKey());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found."));
    }
    
    /**
     * Remove um usuário pelo seu ID.
     * 
     * @param id identificador do usuário a ser removido
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
   
    // ------------------------ SECURITY -----------------------------------------------

    /**
     * Autentica um usuário verificando se o nome e senha informados são válidos.
     * 
     * @param typedUsername nome de usuário informado
     * @param typedPassword senha informada
     * @return {@code true} se as credenciais forem válidas, {@code false} caso contrário
     * @throws RuntimeException se o usuário não for encontrado
     */
    public boolean autenticateUser(String typedUsername, String typedPassword) {
        User user = userRepository.findByUsername(typedUsername)
            .orElseThrow(() -> new RuntimeException("User not found."));

        return passwordEncoder.matches(typedPassword, user.getPassword());
    }
    
    /**
     * Realiza a redefinição da senha do usuário.
     * 
     * <p>Verifica se a senha atual fornecida está correta antes de atualizar para a nova senha.</p>
     * 
     * @param username nome do usuário que terá a senha redefinida
     * @param request objeto contendo a senha atual e a nova senha
     * @return {@code true} se a senha foi atualizada com sucesso, {@code false} caso contrário
     */
    public boolean resetPassword(String username, ResetPasswordRequest request) {
        Optional<User> usuarioOpt = userRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) return false;

        User user = usuarioOpt.get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return false;
        }

        String newPasswordHash = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPasswordHash);
        userRepository.save(user);

        return true;
    }
}
