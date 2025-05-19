package br.com.fiap.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade JPA que representa o usuário no sistema.
 * 
 * <p>
 * Mapeada para a tabela "users" no banco de dados, esta classe armazena as informações
 * de cadastro do usuário, incluindo dados de autenticação, perfil e configurações específicas.
 * </p>
 * 
 * <p>
 * Contém validações de campos importantes para garantir integridade dos dados.
 * </p>
 * 
 * <p><b>Atributos principais:</b></p>
 * <ul>
 *   <li><b>id_user</b>: Identificador único do usuário (chave primária).</li>
 *   <li><b>clientName</b>: Nome do cliente (não pode ser vazio).</li>
 *   <li><b>email</b>: Endereço de e-mail do usuário.</li>
 *   <li><b>registerDate</b>: Data de registro, padrão para data atual.</li>
 *   <li><b>betMaxValue</b>: Valor máximo de aposta permitido (não negativo).</li>
 *   <li><b>username</b>: Nome de usuário único (não pode ser vazio).</li>
 *   <li><b>password</b>: Senha do usuário (não pode ser vazia).</li>
 *   <li><b>userPixKey</b>: Chave Pix associada ao usuário.</li>
 * </ul>
 * 
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /** Identificador único do usuário (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    
    /** Nome do cliente (campo obrigatório). */
    @NotBlank(message = "Client name is mandatory!") 
    private String clientName;
    
    /** Email do usuário. */
    private String email;
    
    /** Data de registro do usuário, padrão para a data atual. */
    private LocalDate registerDate = LocalDate.now();
    
    /** Valor máximo de aposta permitido (deve ser zero ou maior). */
    @Min(value = 0, message = "Bet maximum value cannot be less than 0.") 
    private Double betMaxValue;
    
    /** Nome de usuário único e obrigatório para autenticação. */
    @NotBlank(message = "Client username is mandatory!") 
    @Column(unique = true)
    private String username;
    
    /** Senha do usuário (campo obrigatório). */
    @NotBlank(message = "Client password is mandatory!") 
    private String password;
    
    /** Chave Pix do usuário. */
    private String userPixKey;
}
