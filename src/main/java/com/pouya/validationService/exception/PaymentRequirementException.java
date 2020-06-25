package com.pouya.validationService.exception;

public class PaymentRequirementException extends RuntimeException {

    public PaymentRequirementException() {
    }

    public PaymentRequirementException(String message) {
        super(message);
    }

    public PaymentRequirementException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRequirementException(Throwable cause) {
        super(cause);
    }

    public PaymentRequirementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
