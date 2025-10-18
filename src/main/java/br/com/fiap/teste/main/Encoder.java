package br.com.fiap.teste.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {

	// esta classe é apenas para testes com o uso de encriptografia Bcrypt
	public static void main(String[] args) {
	    System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}


}
