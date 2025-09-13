package br.com.fiap.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailVO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private final String email;
    
    @JsonCreator
    public EmailVO(@JsonProperty("value") String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        // Validação básica de email
        if(!email.contains("@")) {
            throw new IllegalArgumentException("Email is invalid");
        }
        this.email = email.trim();
    }

    public String getValue() {
        return email;
    }
}
