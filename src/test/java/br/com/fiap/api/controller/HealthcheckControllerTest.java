package br.com.fiap.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HealthcheckControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new Healthcheck()).build();
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("API is healthy!"));
    }
}
