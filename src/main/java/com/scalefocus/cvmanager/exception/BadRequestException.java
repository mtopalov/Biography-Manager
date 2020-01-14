package com.scalefocus.cvmanager.exception;

/**
 * @author Mariyan Topalov
 */
public class BadRequestException extends Exception {

    private final String message;

    private final Throwable cause;

    public BadRequestException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
