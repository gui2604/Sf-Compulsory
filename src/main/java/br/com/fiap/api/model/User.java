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

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @NotBlank(message = "Client name is mandatory!") 
    private String clientName;
    private String email;
    private LocalDate registerDate = LocalDate.now();
    @Min(value = 0, message = "Bet maximum value cannot be less than 0.") 
    private Double betMaxValue;
    @NotBlank(message = "Client username is mandatory!") 
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Client password is mandatory!") 
    private String password;
    private String userPixKey;
    
}




