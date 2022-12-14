package com.user.business.exception;

import org.springframework.http.HttpStatus;

/**
 *  Exception when user not found
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class UserNotFoundException extends RuntimeExceptionExtender{
    private final static String ERROR_MESSAGE = "The user doesn't exist";

    /**
     * Genereate exception with standard message
     */
    public UserNotFoundException (){
        super(ERROR_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
