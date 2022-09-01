package com.user.business.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception when mandatory attribut missing
 */
public class MissingParameterException extends RuntimeExceptionExtender {

    /**
     * Generete exception with standard message
     *
     * @param missingParameter missing parameter
     */
    public MissingParameterException(String missingParameter){
        super("Missing parameter : [" + missingParameter + "]", HttpStatus.BAD_REQUEST);
    }
}
