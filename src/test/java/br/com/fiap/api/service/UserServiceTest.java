package br.com.fiap.api.service;

import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.dto.UserCreateDTO;
import br.com.fiap.api.log.LogSummaryService;
import br.com.fiap.api.model.User;
import br.com.fiap.api.repository.UserRepository;
import br.com.fiap.api.vo.EmailVO;
import br.com.fiap.api.vo.PasswordVO;
import br.com.fiap.api.vo.UsernameVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LogSummaryService logSummaryService;

    @InjectMocks
    private UserService userService;

    private UserCreateDTO userCreateDTO;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setClientName("João Silva");
        userCreateDTO.setUsername(new UsernameVO("joaos"));
        userCreateDTO.setPassword(new PasswordVO("123456"));
        userCreateDTO.setEmail(new EmailVO("joao@teste.com"));
        userCreateDTO.setBetMaxValue(5000.0);
        userCreateDTO.setUserPixKey("joaos-pix");

        user = new User();
        user.setId_user(1L);
        user.setUsername("joaos");
        user.setPassword("senha_criptografada");
    }

    @Test
    void deveCriarUsuarioComSenhaCriptografada() {
        when(passwordEncoder.encode("123456")).thenReturn("senha_criptografada");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User salvo = userService.createUser(userCreateDTO);

        assertNotNull(salvo);
        assertEquals("senha_criptografada", salvo.getPassword());
        assertEquals("joaos", salvo.getUsername());
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
        verify(logSummaryService, times(1)).addLog(eq("INFO"), contains("Creating user"));
    }

    @Test
    void deveAutenticarUsuarioCorretamente() {
        user.setPassword("senha_hash");
        when(userRepository.findByUsername("joaos")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha123", "senha_hash")).thenReturn(true);

        boolean autenticado = userService.autenticateUser("joaos", "senha123");

        assertTrue(autenticado);
        verify(userRepository, times(1)).findByUsername("joaos");
        verify(passwordEncoder, times(1)).matches("senha123", "senha_hash");
    }

    @Test
    void deveRetornarFalseParaResetDeSenhaQuandoSenhaAtualIncorreta() {
        user.setPassword("senha_hash");
        when(userRepository.findByUsername("joaos")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senhaErrada", "senha_hash")).thenReturn(false);

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setCurrentPassword("senhaErrada");
        request.setNewPassword("novaSenha");

        boolean result = userService.resetPassword("joaos", request);


        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }
}
