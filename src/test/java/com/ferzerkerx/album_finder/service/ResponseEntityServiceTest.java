package com.ferzerkerx.album_finder.service;

import com.ferzerkerx.album_finder.model.Meta;
import com.ferzerkerx.album_finder.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class ResponseEntityServiceTest {

    private ResponseEntityService responseEntityService;

    @Before
    public void setUp() throws Exception {
        responseEntityService = new ResponseEntityServiceImpl();
    }

    @Test
    public void data() throws Exception {
        String testData = "someData";
        ResponseEntity<Response<String>> response = responseEntityService.data(testData);
        assertNotNull(response);
        assertEquals(testData, response.getBody().getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Meta meta = response.getBody().getMeta();
        assertNull(meta);
    }

    @Test
    public void unexpectedError() throws Exception {
        String errorMessage = "someError";
        ResponseEntity<Response<Object>> response = responseEntityService.unexpectedError(new RuntimeException(errorMessage));
        assertNotNull(response);
        assertNull(response.getBody().getData());

        Meta meta = response.getBody().getMeta();
        assertNotNull(meta);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), meta.getStatus());
    }

    @Test
    public void status() throws Exception {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseEntity<Response<String>> response = responseEntityService.status(httpStatus);
        assertNotNull(response);
        assertEquals(httpStatus, response.getStatusCode());
    }
}