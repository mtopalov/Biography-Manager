package com.scalefocus.cvmanager.controller.advice;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * @author Mariyan Topalov
 */
public class BaseErrorResponse {

    private final String timestamp;

    private final HttpStatus status;

    private final Integer errorCode;

    private final String message;

    private final List<String> errors;

    public BaseErrorResponse(String timestamp, HttpStatus status, Integer errorCode, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    public BaseErrorResponse(String timestamp, HttpStatus status, Integer errorCode, String message, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
