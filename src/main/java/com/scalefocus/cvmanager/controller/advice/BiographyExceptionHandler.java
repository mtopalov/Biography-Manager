package com.scalefocus.cvmanager.controller.advice;

import com.scalefocus.cvmanager.exception.BadRequestException;
import com.scalefocus.cvmanager.exception.BiographyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mariyan Topalov
 */
@ControllerAdvice
public class BiographyExceptionHandler {

    private static final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @ExceptionHandler(BiographyNotFoundException.class)
    public ResponseEntity<Object> handleBiographyNotFound(BiographyNotFoundException ex) {
        String message = ex.getMessage();
        BaseErrorResponse response = new BaseErrorResponse(timestamp, HttpStatus.NOT_FOUND, 404, message, message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        String message = ex.getMessage();
        String error = ex.getCause().getMessage();
        BaseErrorResponse response = new BaseErrorResponse(timestamp, HttpStatus.BAD_REQUEST, 400, message, error);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .filter(errorName -> !"".equals(errorName))
                .distinct()
                .collect(Collectors.toList());
        String message = "Validation failed for: " + ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getRootBeanClass().getSimpleName())
                .distinct()
                .reduce("", (a, b) -> a + b);

        BaseErrorResponse response = new BaseErrorResponse(timestamp, HttpStatus.BAD_REQUEST, 400, message, errors);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        BaseErrorResponse response = new BaseErrorResponse(timestamp, HttpStatus.BAD_REQUEST, 400, message, message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        String message = "Validation failed for: " + ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .reduce("", (acc, newError) -> acc + newError + " ,");

        BaseErrorResponse response = new BaseErrorResponse(timestamp, HttpStatus.BAD_REQUEST, 400, message, errors);
        return new ResponseEntity<>(response, response.getStatus());
    }
}