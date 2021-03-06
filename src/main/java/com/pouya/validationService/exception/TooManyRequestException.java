package com.pouya.validationService.exception;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException() {
    }

    public TooManyRequestException(String message) {
        super(message);
    }

    public TooManyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyRequestException(Throwable cause) {
        super(cause);
    }

    public TooManyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
