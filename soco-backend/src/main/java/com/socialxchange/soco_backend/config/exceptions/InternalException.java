package com.socialxchange.soco_backend.config.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalException extends Exception {
    public InternalException(String message) {
        super(message);
        log.error("Exception occurred: {}", message);
    }
}
