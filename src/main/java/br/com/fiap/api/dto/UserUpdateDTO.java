package br.com.fiap.api.dto;

import br.com.fiap.api.vo.EmailVO;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String clientName;
    private EmailVO email;
    @Min(value = 0)
    private Double betMaxValue;
    private String userPixKey;
}
