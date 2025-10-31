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
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadEmailForm() throws Exception {
        mockMvc.perform(get("/email-check"))
                .andExpect(status().isOk())
                .andExpect(view().name("email-form"));
    }

    @Test
    void shouldAcceptValidEmail() throws Exception {
        mockMvc.perform(post("/submit").param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result", "✅ test@example.com is valid and the domain exists!"))
                .andExpect(view().name("result"));
    }

    @Test
    void shouldRejectInvalidEmail() throws Exception {
        mockMvc.perform(post("/submit").param("email", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result", "❌ invalid-email is not a valid email format."))
                .andExpect(view().name("result"));
    }
}
