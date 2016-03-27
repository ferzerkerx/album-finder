package com.ferzerkerx.album_finder.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppControllerTest extends BaseControllerTest {

    @Test
    public void logout() throws Exception {
        getMockMvc().perform(get("/logout")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
        ; //
    }

    @Test
    public void getUserInfoAdmin() throws Exception {
        getMockMvc().perform(get("/user")//
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.username").value("admin"))
            .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMIN"))
        ; //
    }

    @Test
    public void getUserInfoUser() throws Exception {
        getMockMvc().perform(get("/user")//
            .with(authenticatedUser())
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_AUTHENTICATED_USER"))
        ; //
    }
}