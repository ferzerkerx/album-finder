package com.ferzerkerx.albumfinder.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@UtilityClass
public final class TestUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static RequestPostProcessor admin() {
        return user("admin").password("password").roles("ADMIN");
    }

    static RequestPostProcessor authenticatedUser() {
        return user("user").password("password").roles("AUTHENTICATED_USER");
    }

    static byte[] toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }
}
