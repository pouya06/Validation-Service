package com.pouya.validationService.exception;

public class NotValidPostalAddressException extends RuntimeException {
    public NotValidPostalAddressException() {
    }

    public NotValidPostalAddressException(String message) {
        super(message);
    }

    public NotValidPostalAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidPostalAddressException(Throwable cause) {
        super(cause);
    }

    public NotValidPostalAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
