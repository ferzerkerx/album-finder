package com.ferzerkerx.albumfinder.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppControllerTest extends BaseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void logout() throws Exception {
        MockMvc mockMvc = getMockMvc();
        MockHttpServletRequestBuilder requestBuilder = get("/logout").contentType(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(requestBuilder);

            perform
            .andExpect(status().isOk());
    }

    @Test
    public void getUserInfoAdmin() throws Exception {
        getMockMvc().perform(get("/user")
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("admin"))
            .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMIN"));
    }

    @Test
    public void getUserInfoUser() throws Exception {
        getMockMvc().perform(get("/user")
            .with(authenticatedUser())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_AUTHENTICATED_USER"));
    }
}