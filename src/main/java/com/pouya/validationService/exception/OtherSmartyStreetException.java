package com.pouya.validationService.exception;

public class OtherSmartyStreetException extends RuntimeException {
    public OtherSmartyStreetException() {
    }

    public OtherSmartyStreetException(String message) {
        super(message);
    }

    public OtherSmartyStreetException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtherSmartyStreetException(Throwable cause) {
        super(cause);
    }

    public OtherSmartyStreetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
