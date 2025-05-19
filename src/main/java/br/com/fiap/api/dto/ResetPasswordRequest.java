package br.com.fiap.api.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) utilizado para representar os dados necessários
 * para realizar a troca de senha de um usuário.
 * 
 * <p>
 * Esta classe é usada em requisições PATCH para o endpoint
 * {@code /auth/{username}/password}.
 * </p>
 * 
 * <p>Contém os seguintes campos:</p>
 * <ul>
 *   <li><b>currentPassword</b>: Senha atual do usuário, usada para validação.</li>
 *   <li><b>newPassword</b>: Nova senha que substituirá a anterior.</li>
 * </ul>
 * 
 * <p>
 * A anotação {@code @Data} do Lombok gera automaticamente os métodos
 * {@code getters}, {@code setters}, {@code equals()}, {@code hashCode()} e {@code toString()}.
 * </p>
 * 
 * @see br.com.fiap.api.controller.AuthController
 * 
 * @since 1.0
 */
@Data
public class ResetPasswordRequest {
    
    /** Senha atual do usuário. */
    private String currentPassword;

    /** Nova senha a ser definida. */
    private String newPassword;
}
