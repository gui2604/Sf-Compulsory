package br.com.fiap.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe responsável pela configuração de segurança da aplicação.
 * <p>
 * Define as regras de autenticação, autorização, codificação de senha e 
 * gerenciamento de usuários. Utiliza a API do Spring Security.
 * </p>
 * 
 * <ul>
 *   <li>Permite acesso irrestrito ao H2 Console e endpoints de documentação Swagger.</li>
 *   <li>Exige autenticação básica para as demais requisições.</li>
 *   <li>Desabilita CSRF e Frame Options para facilitar o uso do H2 Console.</li>
 * </ul>
 * 
 */
@Configuration
public class SecurityConfig {

    /**
     * Define o bean {@link PasswordEncoder} usado para codificar senhas com BCrypt.
     *
     * @return Uma instância de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança HTTP da aplicação.
     * 
     * <p>
     * Permite acesso aos seguintes endpoints sem autenticação:
     * <ul>
     *   <li>/h2-console/**</li>
     *   <li>/api/healthcheck</li>
     *   <li>/swagger-ui/**</li>
     *   <li>/v3/api-docs/**</li>
     * </ul>
     * Requer autenticação para quaisquer outros endpoints.
     * </p>
     * 
     * <p>
     * Também desabilita CSRF e desativa as proteções de frame para permitir o uso do H2 Console.
     * </p>
     *
     * @param http Instância de {@link HttpSecurity} fornecida pelo Spring.
     * @return Uma {@link SecurityFilterChain} configurada.
     * @throws Exception Em caso de falha na configuração de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/api/healthcheck").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {}) 
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    /**
     * Define um {@link UserDetailsService} que retorna um usuário estático com papel USER.
     * 
     * <p>Esse método cria um único usuário com nome <code>user</code> e senha <code>1234</code> (criptografada).</p>
     *
     * @param passwordEncoder Encoder usado para codificar a senha.
     * @return Uma instância de {@link UserDetailsService} com um usuário embutido.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> User.builder()
            .username("user")
            .password(passwordEncoder.encode("1234"))  // Criptografa a senha
            .roles("USER")
            .build();
    }
}
