package com.scalefocus.cvmanager.exception;

/**
 * Exception raised when a username already exists in the database.
 *
 * @author Mariyan Topalov
 */
public class UsernameExistsException extends Exception {

    public UsernameExistsException(String message) {
        super(message);
    }
}
