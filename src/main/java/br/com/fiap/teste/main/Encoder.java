package br.com.fiap.teste.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {

	public static void main(String[] args) {
	    System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}


}
