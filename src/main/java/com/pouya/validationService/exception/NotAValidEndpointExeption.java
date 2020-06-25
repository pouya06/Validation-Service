package com.pouya.validationService.exception;

public class NotAValidEndpointExeption extends RuntimeException {
    public NotAValidEndpointExeption() {
    }

    public NotAValidEndpointExeption(String message) {
        super(message);
    }

    public NotAValidEndpointExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAValidEndpointExeption(Throwable cause) {
        super(cause);
    }

    public NotAValidEndpointExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
