package br.com.fiap.teste.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe utilitária para gerar um hash bcrypt de uma senha em texto simples.
 * 
 * <p>Esta classe contém um método main que imprime no console o hash da senha
 * fixa "1234" utilizando o {@link BCryptPasswordEncoder} do Spring Security.</p>
 * 
 * <p>Útil para testar e obter uma senha criptografada para uso em testes ou
 * configuração inicial.</p>
 * 
 */
public class Encoder {

    /**
     * Método principal que gera e imprime o hash bcrypt da senha "1234".
     * 
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }
}
