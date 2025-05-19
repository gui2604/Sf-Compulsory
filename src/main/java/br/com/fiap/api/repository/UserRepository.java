package br.com.fiap.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.api.model.User;

/**
 * Repositório Spring Data JPA para a entidade {@link User}.
 * 
 * <p>
 * Fornece operações CRUD padrão e métodos personalizados para busca
 * de usuários por atributos específicos.
 * </p>
 * 
 * <p><b>Métodos personalizados:</b></p>
 * <ul>
 *   <li>{@code findByUsername(String username)}: Busca um usuário pelo nome de usuário.</li>
 *   <li>{@code findByEmail(String email)}: Busca um usuário pelo e-mail.</li>
 * </ul>
 * 
 * <p>O nome dos métodos deve seguir a convenção do Spring Data para
 * que a implementação seja gerada automaticamente.</p>
 * 
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo nome de usuário.
     * 
     * @param username o nome de usuário a ser buscado
     * @return um {@link Optional} contendo o usuário se encontrado, ou vazio caso contrário
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca um usuário pelo e-mail.
     * 
     * @param email o e-mail a ser buscado
     * @return um {@link Optional} contendo o usuário se encontrado, ou vazio caso contrário
     */
    Optional<User> findByEmail(String email);
}
