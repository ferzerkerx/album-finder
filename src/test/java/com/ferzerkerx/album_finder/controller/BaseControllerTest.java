package com.ferzerkerx.album_finder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferzerkerx.album_finder.BaseIntegrationTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


public class BaseControllerTest extends BaseIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = createMockMvc();
    }


    MockMvc getMockMvc() {
        return mockMvc;
    }

    WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    private MockMvc createMockMvc() {
        return MockMvcBuilders.webAppContextSetup(getWebApplicationContext()).apply(springSecurity()).build();
    }



    static RequestPostProcessor admin() {
        return user("admin").password("password").roles("ADMIN");
    }

    static RequestPostProcessor authenticatedUser() {
        return user("user").password("password").roles("AUTHENTICATED_USER");
    }

    static byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
