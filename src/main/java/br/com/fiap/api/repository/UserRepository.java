package br.com.fiap.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username); //o nome do método deve possuir o nome exato do atributo
	Optional<User> findByEmail(String email); 
}