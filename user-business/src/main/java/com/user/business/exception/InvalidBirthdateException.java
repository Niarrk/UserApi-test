package com.user.business.exception;

/**
 * Exception when user's birthdate is not respected
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class InvalidBirthdateException extends InvalidJsonException {

    private final static String ERROR_MESSAGE = "Birthdate is incorrect : user must be an adult.";

    /**
     * Create exception with standards message
     */
    public InvalidBirthdateException(){
        super(ERROR_MESSAGE);
    }
}
