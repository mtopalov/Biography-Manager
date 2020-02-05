package com.scalefocus.cvmanager.exception;

/**
 * Exception raised when the {@link org.springframework.http.HttpRequest} is not valid.
 *
 * @author Mariyan Topalov
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }
}
