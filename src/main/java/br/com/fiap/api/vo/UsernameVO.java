package br.com.fiap.api.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class UsernameVO {

	@NotBlank(message = "Username cannot be blank")
	private final String username;

	@JsonCreator
	public UsernameVO(@JsonProperty("value") String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be blank");
		}
		this.username = username.trim();
	}

	public String getValue() {
		return username;
	}
}
