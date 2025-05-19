package br.com.fiap.api.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) utilizado para capturar as informações de login
 * enviadas pelo cliente na requisição HTTP.
 * 
 * <p>
 * Esta classe é usada principalmente no endpoint {@code POST /auth/login}.
 * </p>
 *
 * <p>Contém os seguintes campos:</p>
 * <ul>
 *   <li><b>username</b>: Nome de usuário ou identificador para autenticação.</li>
 *   <li><b>password</b>: Senha correspondente ao usuário.</li>
 * </ul>
 * 
 * <p>Utiliza anotações do Lombok para geração automática de getters, setters, 
 * {@code equals()}, {@code hashCode()} e {@code toString()}.</p>
 * 
 */
@Data
public class LoginRequest {
    /** Nome de usuário para autenticação. */
    private String username;

    /** Senha do usuário para autenticação. */
    private String password;
}
