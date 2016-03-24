package com.ferzerkerx.album_finder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferzerkerx.album_finder.BaseIntegrationTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseControllerTest extends BaseIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = createMockMvc();
    }


    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    private MockMvc createMockMvc() {
        return MockMvcBuilders.webAppContextSetup(getWebApplicationContext()).build();
    }

    protected static byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
