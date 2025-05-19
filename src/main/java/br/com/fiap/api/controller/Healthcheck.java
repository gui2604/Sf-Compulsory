package br.com.fiap.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável pelo endpoint de verificação da saúde da API.
 * <p>
 * Este endpoint é normalmente utilizado por sistemas de monitoramento ou 
 * por desenvolvedores/testes automatizados para garantir que a API está funcionando.
 * </p>
 * 
 * <p><b>Endpoint:</b></p>
 * <ul>
 *   <li>GET <code>/api/healthcheck</code>: Retorna status 200 com mensagem indicando que a API está saudável.</li>
 * </ul>
 * 
 */
@RestController
@RequestMapping("/api")
public class Healthcheck {

    /**
     * Endpoint de verificação de saúde da API.
     *
     * @return {@link ResponseEntity} com status 200 e mensagem "API is healthy!".
     */
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity
                .status(200)
                .body("API is healthy!");
    }
}
