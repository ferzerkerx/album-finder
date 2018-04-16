package com.ferzerkerx.albumfinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public final class TestUtil {

    private TestUtil() {
        throw new AssertionError();
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
