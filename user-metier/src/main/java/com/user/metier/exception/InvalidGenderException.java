package com.user.metier.exception;

import java.util.List;

/**
 * Exception, when user's gender is not respected
 */
public class InvalidGenderException extends InvalidJsonException {

    private final static String ERROR_MESSAGE = "Gender is incorrect : must be ";

    /**
     * Create exception with standard message
     *
     * @param genderList List of available genders
     */
    public InvalidGenderException(List<String> genderList){
        super(ERROR_MESSAGE + genderList.toString());
    }
}
