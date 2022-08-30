package com.user.business.exception;

/**
 * Exception when user's phone number format is wrong
 */
public class InvalidPhoneNumberException extends InvalidJsonException {

    private final static String ERROR_MESSAGE = "Phone is incorrect : format must be xx-xx-xx-xx-xx";

    /**
     * Generate exception with standard message
     */
    public InvalidPhoneNumberException(){
        super(ERROR_MESSAGE);
    }

}
