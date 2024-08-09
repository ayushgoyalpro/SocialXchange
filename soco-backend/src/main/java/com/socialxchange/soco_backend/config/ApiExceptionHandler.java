package com.socialxchange.soco_backend.config;

import com.socialxchange.soco_backend.config.dto.Exception;
import com.socialxchange.soco_backend.config.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<?> handleInternalException(InternalException e) {
        Exception exception = new Exception();
        exception.setMessage(e.getMessage());
        exception.setType("InternalException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
