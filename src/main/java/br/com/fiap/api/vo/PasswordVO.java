package br.com.fiap.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordVO {

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, message = "Password must have at least 4 characters")
    private final String password;
    
    @JsonCreator
    public PasswordVO(@JsonProperty("value") String password) {
        if(password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must have at least 4 characters");
        }
        this.password = password;
    }

    public String getValue() {
        return password;
    }
}
