package com.scalefocus.cvmanager.exception;

/**
 * Exception raised when credentials for authentication are missing.
 *
 * @author Mariyan Topalov
 */
public class MissingCredentialsException extends Exception {

    public MissingCredentialsException(String message) {
        super(message);
    }

}
