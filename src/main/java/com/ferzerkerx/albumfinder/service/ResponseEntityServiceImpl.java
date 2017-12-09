package com.ferzerkerx.albumfinder.service;

import com.ferzerkerx.albumfinder.model.BaseException;
import com.ferzerkerx.albumfinder.model.Meta;
import com.ferzerkerx.albumfinder.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
class ResponseEntityServiceImpl implements ResponseEntityService {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseEntityServiceImpl.class);

    @Override
    public <T> ResponseEntity<Response<T>> data(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public <T> ResponseEntity<Response<T>> status(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    @Override
    public ResponseEntity<Response<Object>> unexpectedError(Exception e) {
        logException(e);
        String message = "Unexpected Error";
        HttpStatus status  = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof BaseException) {
            message = e.getMessage();
            status = HttpStatus.OK;
        }
        return error(message, status);
    }

    private void logException(Exception e) {
        LOG.error(e.getMessage());
    }

    private <B> ResponseEntity<Response<B>> error(String error, HttpStatus status) {
        Response<B> response = new Response<>();
        response.setMeta(createMetaForError(error, status));
        return new ResponseEntity<>(response, status);
    }

    private Meta createMetaForError(String error, HttpStatus status) {
        Meta meta = new Meta();
        meta.setErrorMessage(error);
        meta.setStatus(status.value());
        return meta;
    }
}
