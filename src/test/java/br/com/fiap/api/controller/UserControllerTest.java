package br.com.fiap.api.controller;

import br.com.fiap.api.dto.UserCreateDTO;
import br.com.fiap.api.dto.UserUpdateDTO;
import br.com.fiap.api.model.User;
import br.com.fiap.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId_user(1L);
        user1.setUsername("joaos");
        user1.setClientName("João Silva");
        user1.setPassword("123456");
    }
    @Test
    void testListAllUsers() throws Exception {
        when(userService.listAll()).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("joaos"))
                .andExpect(jsonPath("$[0].clientName").value("João Silva"));
    }
    @Test
    void testCreateUser() throws Exception {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setClientName("João Silva");
        dto.setUsername(new br.com.fiap.api.vo.UsernameVO("joaos"));
        dto.setPassword(new br.com.fiap.api.vo.PasswordVO("123456"));

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(user1);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("joaos"))
                .andExpect(jsonPath("$.clientName").value("João Silva"));
    }
    @Test
    void testSearchUserById_UserExists() throws Exception {
        when(userService.searchForId(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("joaos"))
                .andExpect(jsonPath("$.clientName").value("João Silva"));
    }

    @Test
    void testSearchUserById_UserNotFound() throws Exception {
        when(userService.searchForId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testUpdateUser_UserExists() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setClientName("João Atualizado");

        user1.setClientName("João Atualizado");
        when(userService.updateUser(Mockito.eq(1L), any(UserUpdateDTO.class))).thenReturn(user1);

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("João Atualizado"));
    }

    @Test
    void testUpdateUser_UserNotFound() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setClientName("João Atualizado");

        when(userService.updateUser(Mockito.eq(99L), any(UserUpdateDTO.class)))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(put("/api/v1/users/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
    @Test
    void testPatchUser_UserExists() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setClientName("João Parcial");

        user1.setClientName("João Parcial");
        when(userService.updateUser(Mockito.eq(1L), any(UserUpdateDTO.class))).thenReturn(user1);

        mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("João Parcial"));
    }

    @Test
    void testPatchUser_UserNotFound() throws Exception {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setClientName("João Parcial");

        when(userService.updateUser(Mockito.eq(99L), any(UserUpdateDTO.class)))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(patch("/api/v1/users/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDeleteUser_UserExists() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_UserNotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("User not found"))
                .when(userService).delete(99L);

        mockMvc.perform(delete("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }






}
