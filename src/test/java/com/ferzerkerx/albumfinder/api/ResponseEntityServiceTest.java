package com.ferzerkerx.albumfinder.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


class ResponseEntityServiceTest {

    @Test
    void data() {
        String testData = "someData";
        ResponseEntity<Response<String>> response = ResponseUtils.data(testData);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(testData, response.getBody().getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Meta meta = response.getBody().getMeta();
        assertNull(meta);
    }

    @Test
    void unexpectedError() {
        String errorMessage = "someError";
        ResponseEntity<Response<Object>> response = ResponseUtils.unexpectedError(new RuntimeException(errorMessage));
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNull(response.getBody().getData());

        Meta meta = response.getBody().getMeta();
        assertNotNull(meta);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), meta.getStatus());
    }

}