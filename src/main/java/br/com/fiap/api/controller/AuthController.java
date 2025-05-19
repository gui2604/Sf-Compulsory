package br.com.fiap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api.dto.LoginRequest;
import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.service.UserService;

/**
 * Controlador responsável pelos endpoints de autenticação e redefinição de senha.
 * <p>
 * Oferece endpoints REST para login de usuário e atualização de senha, utilizando o serviço {@link UserService}.
 * </p>
 *
 * <p><b>Endpoints:</b></p>
 * <ul>
 *   <li>POST <code>/auth/login</code>: Autenticação de usuário.</li>
 *   <li>PATCH <code>/auth/{username}/password</code>: Redefinição de senha.</li>
 * </ul>
 * 
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Realiza a autenticação de um usuário com base nas credenciais fornecidas.
     *
     * @param loginUserRequest Objeto contendo o nome de usuário e a senha.
     * @return {@link ResponseEntity} com status 200 se autenticado ou 401 se falhar.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginUserRequest) {
        boolean autenticated = userService.autenticateUser(
            loginUserRequest.getUsername(), loginUserRequest.getPassword());

        if (autenticated) {
            return ResponseEntity.ok("Login succeeded");
        } else {
            return ResponseEntity.status(401).body("Not authorized. Invalid credentials.");
        }
    }

    /**
     * Redefine a senha de um usuário existente.
     *
     * @param username Nome de usuário cujo password será atualizado.
     * @param request Objeto com os dados necessários para atualizar a senha.
     * @return {@link ResponseEntity} com status 200 se sucesso ou 401 se não autorizado.
     */
    @PatchMapping("/{username}/password")
    public ResponseEntity<String> resetPassword(
            @PathVariable String username,
            @RequestBody ResetPasswordRequest request) {

        boolean updatedPassword = userService.resetPassword(username, request);

        if (updatedPassword) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(401).body("Not authorized to perform this action.");
        }
    }
}
