package br.com.fiap.api.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.fiap.api.dto.LoginRequest;
import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.service.UserService;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void testLogin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("joaos");
        loginRequest.setPassword("123456");

        when(userService.autenticateUser(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login succeeded"));
    }

    @Test
    void testLogin_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("joaos");
        loginRequest.setPassword("wrongpass");

        when(userService.autenticateUser(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Not authorized. Invalid credentials."));
    }
    @Test
    void testResetPassword_Success() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setCurrentPassword("oldPass");
        request.setNewPassword("newPass");

        when(userService.resetPassword(anyString(), org.mockito.Mockito.any(ResetPasswordRequest.class)))
                .thenReturn(true);

        mockMvc.perform(patch("/auth/joaos/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password updated successfully."));
    }

    @Test
    void testResetPassword_Failure() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setCurrentPassword("oldPass");
        request.setNewPassword("newPass");

        when(userService.resetPassword(anyString(), org.mockito.Mockito.any(ResetPasswordRequest.class)))
                .thenReturn(false);

        mockMvc.perform(patch("/auth/joaos/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Not authorized to perform this action."));
    }


}
