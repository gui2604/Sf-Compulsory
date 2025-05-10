package br.com.fiap.api.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	private String currentPassword;
	private String newPassword;
}
