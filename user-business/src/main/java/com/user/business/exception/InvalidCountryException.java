package com.user.business.exception;

import java.util.List;

/**
 * Exception when country is not respected
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class InvalidCountryException extends InvalidJsonException {

    private final static String ERROR_MESSAGE = "Country is incorrect : user must be from ";

    /**
     * Crete exception with standard message
     * @param params List of available country
     */
    public InvalidCountryException(List<String> params){
        super(ERROR_MESSAGE + params.toString());
    }
}
