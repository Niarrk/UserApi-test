package com.user.metier.exception;

/**
 * Exception when mandatory attribut missing
 */
public class MissingParameterException extends RuntimeException {

    /**
     * Generete exception with standard message
     *
     * @param missingParameter missing parameter
     */
    public MissingParameterException(String missingParameter){
        super("Missing parameter : [" + missingParameter + "]");
    }
}
