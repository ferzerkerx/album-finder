package com.ferzerkerx.albumfinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferzerkerx.albumfinder.TestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public abstract class BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected MockMvc getMockMvc() {
        return mockMvc;
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
