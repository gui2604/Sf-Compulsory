package br.com.fiap.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.api.model.User;
import br.com.fiap.api.service.UserService;

/**
 * Controlador REST responsável por gerenciar os usuários da aplicação.
 * 
 * <p>
 * Fornece endpoints para criação, leitura, atualização total e parcial, e exclusão de usuários.
 * As requisições são processadas com base nos serviços da classe {@link UserService}.
 * </p>
 * 
 * <p><b>Endpoints:</b></p>
 * <ul>
 *   <li>GET     /api/v1/users           → Listar todos os usuários</li>
 *   <li>GET     /api/v1/users/{id}      → Buscar usuário por ID</li>
 *   <li>POST    /api/v1/users           → Criar novo usuário</li>
 *   <li>PUT     /api/v1/users/{id}      → Atualizar completamente um usuário</li>
 *   <li>PATCH   /api/v1/users/{id}      → Atualizar parcialmente um usuário</li>
 *   <li>DELETE  /api/v1/users/{id}      → Deletar usuário</li>
 * </ul>
 * 
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Lista de objetos {@link User}.
     */
    @GetMapping
    public List<User> listAllUsers() {
        return userService.listAll();
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id Identificador do usuário.
     * @return {@link ResponseEntity} contendo o usuário, ou 404 se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> searchUserForId(@PathVariable Long id) {
        return userService.searchForId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo usuário.
     *
     * @param user Objeto {@link User} contendo os dados do novo usuário.
     * @return {@link ResponseEntity} com o usuário criado (201) ou erro de validação (422).
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    /**
     * Remove um usuário com base no ID.
     *
     * @param id Identificador do usuário.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * Atualiza completamente os dados de um usuário existente.
     *
     * @param id ID do usuário a ser atualizado.
     * @param updatedUser Objeto {@link User} com os novos dados.
     * @return {@link ResponseEntity} com o usuário atualizado ou 404 se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUser = userService.searchForId(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setClientName(updatedUser.getClientName());
            user.setEmail(updatedUser.getEmail());
            user.setBetMaxValue(updatedUser.getBetMaxValue());
            user.setUserPixKey(updatedUser.getUserPixKey());

            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza parcialmente os dados de um usuário existente.
     *
     * @param id ID do usuário a ser atualizado.
     * @param parcialUser Objeto {@link User} com os campos a serem atualizados.
     * @return {@link ResponseEntity} com o usuário atualizado ou 404 se não encontrado.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserField(@PathVariable Long id, @RequestBody User parcialUser) {
        Optional<User> existingUser = userService.searchForId(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (parcialUser.getClientName() != null) {
                user.setClientName(parcialUser.getClientName());
            }
            if (parcialUser.getEmail() != null) {
                user.setEmail(parcialUser.getEmail());
            }
            if (parcialUser.getBetMaxValue() != null) {
                user.setBetMaxValue(parcialUser.getBetMaxValue());
            }
            if (parcialUser.getUserPixKey() != null) {
                user.setUserPixKey(parcialUser.getUserPixKey());
            }

            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
