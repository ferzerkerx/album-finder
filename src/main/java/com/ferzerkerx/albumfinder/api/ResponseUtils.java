package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.api.dto.Meta;
import com.ferzerkerx.albumfinder.api.dto.Response;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
@Slf4j
public class ResponseUtils {

    public static <T> ResponseEntity<Response<T>> data(@NonNull T data) {
        final Response<T> response = Response.<T>builder()
                .data(data)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Response<Object>> unexpectedError(@NonNull Exception e) {
        log.error(e.getMessage());
        String message = "Unexpected Error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return error(message, status);
    }

    private static <T> ResponseEntity<Response<T>> error(@NonNull String error, @NonNull HttpStatus status) {
        final Response<T> response = Response.<T>builder()
                .meta(createMetaForError(error, status))
                .build();

        return new ResponseEntity<>(response, status);
    }

    private static Meta createMetaForError(@NonNull String error, @NonNull HttpStatus status) {
        return Meta.builder()
                .errorMessage(error)
                .status(status.value())
                .build();
    }
}
