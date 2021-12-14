package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.config.SecurityWebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ferzerkerx.albumfinder.api.TestUtil.admin;
import static com.ferzerkerx.albumfinder.api.TestUtil.authenticatedUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityWebConfig.class})
@WebMvcTest(controllers = AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(authenticatedUser()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void adminLogin() throws Exception {
        mockMvc.perform(post("/user")
                        .with(admin()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMIN"));
    }

    @Test
    void userLogin() throws Exception {
        mockMvc.perform(post("/user")
                        .with(authenticatedUser()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_AUTHENTICATED_USER"));
    }
}