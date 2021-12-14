package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public ResponseUtils() {
        throw new AssertionError();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    public static <T> ResponseEntity<Response<T>> data(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> status(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    public static ResponseEntity<Response<Object>> unexpectedError(Exception e) {
        LOGGER.error(e.getMessage());
        String message = "Unexpected Error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof BaseException) { //TODO this needs to be improved
            message = e.getMessage();
            status = HttpStatus.OK;
        }
        return error(message, status);
    }

    private static <B> ResponseEntity<Response<B>> error(String error, HttpStatus status) {
        Response<B> response = new Response<>();
        response.setMeta(createMetaForError(error, status));
        return new ResponseEntity<>(response, status);
    }

    private static Meta createMetaForError(String error, HttpStatus status) {
        Meta meta = new Meta();
        meta.setErrorMessage(error);
        meta.setStatus(status.value());
        return meta;
    }
}
