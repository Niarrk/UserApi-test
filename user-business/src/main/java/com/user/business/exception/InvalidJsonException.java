package com.user.business.exception;

/**
 * Exception for invalid json parameters
 */
public class InvalidJsonException extends RuntimeException {
    /**
     * Generate exception with message
     * @param message message
     */
    public InvalidJsonException (String message){
         super(message);
    }
}
