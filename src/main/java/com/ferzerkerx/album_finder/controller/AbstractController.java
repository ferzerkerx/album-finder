package com.ferzerkerx.album_finder.controller;

import com.ferzerkerx.album_finder.model.Response;
import com.ferzerkerx.album_finder.service.ResponseEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    private final ResponseEntityService responseEntityService;

    protected AbstractController(ResponseEntityService responseEntityService) {
        this.responseEntityService = responseEntityService;
    }

    protected <T> ResponseEntity<Response<T>> data(T data) {
        return responseEntityService.data(data);
    }

    public <T> ResponseEntity<Response<T>> status(HttpStatus status) {
        return responseEntityService.status(status);
    }
}
