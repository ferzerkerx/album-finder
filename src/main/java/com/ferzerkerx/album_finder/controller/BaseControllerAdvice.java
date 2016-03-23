package com.ferzerkerx.album_finder.controller;

import com.ferzerkerx.album_finder.model.Response;
import com.ferzerkerx.album_finder.service.ResponseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseControllerAdvice {

    @Autowired
    private ResponseEntityService _responseEntityService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleUncaughtException(Exception e) throws Exception {
        return _responseEntityService.unexpectedError(e);
    }
}
