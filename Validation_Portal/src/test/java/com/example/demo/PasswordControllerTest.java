package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadPasswordForm() throws Exception {
        mockMvc.perform(get("/password-check"))
                .andExpect(status().isOk())
                .andExpect(view().name("password-form"));
    }

    @Test
    void shouldReturnStrongPasswordMessage() throws Exception {
        mockMvc.perform(post("/check-password").param("password", "StrongP@ss123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result", "✅ Strong password!"))
                .andExpect(view().name("result"));
    }

    @Test
    void shouldReturnWeakPasswordMessage() throws Exception {
        mockMvc.perform(post("/check-password").param("password", "abc"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result",
                        "❌ Weak password. Try adding numbers, symbols, and upper/lowercase letters."))
                .andExpect(view().name("result"));
    }
}

