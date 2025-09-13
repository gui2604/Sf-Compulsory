package br.com.fiap.api.dto;

import br.com.fiap.api.vo.EmailVO;
import br.com.fiap.api.vo.PasswordVO;
import br.com.fiap.api.vo.UsernameVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotBlank
    private String clientName;

    private EmailVO email;

    @Min(value = 0)
    private Double betMaxValue;

    private UsernameVO username;

    private PasswordVO password;

    private String userPixKey;
}
