package com.ferzerkerx.album_finder.service;

import com.ferzerkerx.album_finder.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseEntityService {
    <T> ResponseEntity<Response<T>> data(T data);

    <T> ResponseEntity<Response<T>> status(HttpStatus status);

    ResponseEntity<Response<Object>> unexpectedError(Exception e);
}
