package com.scalefocus.cvmanager.model.error;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Basic error response that will be retrieved to the client when an error occurs.
 *
 * @author Mariyan Topalov
 */
public final class BaseErrorResponse {

    private final String timestamp;

    private final HttpStatus status;

    private final Integer errorCode;

    private final String message;

    private final List<String> errors;

    public BaseErrorResponse(String timestamp, HttpStatus status, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = status.value();
        this.message = message;
        this.errors = errors;
    }

    public BaseErrorResponse(String timestamp, HttpStatus status, String message, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = status.value();
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


    @SuppressWarnings("unchecked")
    public JSONObject asJson() {
        JSONObject result = new JSONObject();
        result.put("timestamp", timestamp);
        result.put("status", status.name());
        result.put("error_code", errorCode);
        result.put("message", message);
        result.put("errors", String.join(", ", errors));
        return result;
    }
}
