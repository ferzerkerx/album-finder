package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.model.Response;
import com.ferzerkerx.albumfinder.service.ResponseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseControllerAdvice {

    private final ResponseEntityService responseEntityService;

    @Autowired
    public BaseControllerAdvice(ResponseEntityService responseEntityService) {
        this.responseEntityService = responseEntityService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleUncaughtException(Exception e) {
        return responseEntityService.unexpectedError(e);
    }
}
