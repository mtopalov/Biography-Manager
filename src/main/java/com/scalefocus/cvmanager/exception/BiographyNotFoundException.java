package com.scalefocus.cvmanager.exception;

/**
 * @author Mariyan Topalov
 */
public class BiographyNotFoundException extends Exception {

    private final String message;


    public BiographyNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
