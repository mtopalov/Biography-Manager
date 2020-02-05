package com.scalefocus.cvmanager.exception;

/**
 * Exception raised when a given file format is not supported.
 *
 * @author Mariyan Topalov
 */
public class WrongFormatException extends Exception {

    public WrongFormatException(String message) {
        super(message);
    }
}
