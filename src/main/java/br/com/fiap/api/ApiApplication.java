package br.com.fiap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * <p>
 * Esta classe serve como ponto de entrada para a execução da aplicação.
 * Ela utiliza a anotação {@link SpringBootApplication} para indicar que
 * trata-se de uma aplicação Spring Boot com configuração automática.
 * </p>
 * 
 * @author Tony Willian
 * @author Henrique Parra
 * @author Guilherme Barreto
 * @author Roberto Oliveira
 * @author Nicolas Oliveira
 * @since 1.0
 */
@SpringBootApplication
public class ApiApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando passados na execução da aplicação.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
