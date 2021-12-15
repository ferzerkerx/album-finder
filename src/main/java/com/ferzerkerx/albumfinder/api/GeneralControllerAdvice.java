package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.api.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleUncaughtException(Exception e) {
        return ResponseUtils.unexpectedError(e);
    }
}
