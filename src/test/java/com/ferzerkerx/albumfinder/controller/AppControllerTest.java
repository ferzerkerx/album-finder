package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.ferzerkerx.albumfinder.controller.TestUtil.admin;
import static com.ferzerkerx.albumfinder.controller.TestUtil.authenticatedUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebMvcTest(AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUserInfoAdmin() throws Exception {
        mockMvc.perform(get("/user")
                .with(admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMIN"));
    }

    @Test
    void getUserInfoUser() throws Exception {
        mockMvc.perform(get("/user")
                .with(authenticatedUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_AUTHENTICATED_USER"));
    }
}