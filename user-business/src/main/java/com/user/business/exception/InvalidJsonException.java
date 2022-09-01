package com.user.business.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for invalid json parameters
 */
public class InvalidJsonException extends RuntimeExceptionExtender {
    /**
     * Generate exception with message
     * @param message message
     */
    public InvalidJsonException (String message){
         super(message, HttpStatus.BAD_REQUEST);
    }
}
