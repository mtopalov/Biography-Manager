package com.scalefocus.cvmanager.exception;

/**
 * Exception raised when a {@link com.scalefocus.cvmanager.model.biography.Biography} is not found(does not exist).
 *
 * @author Mariyan Topalov
 */
public class BiographyNotFoundException extends Exception {

    public BiographyNotFoundException(String message) {
        super(message);
    }
}
