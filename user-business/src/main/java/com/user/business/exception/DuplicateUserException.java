package com.user.business.exception;

import com.user.data.dto.UserDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * DuplicateUserException
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class DuplicateUserException extends RuntimeExceptionExtender{
    private final static String ERROR_MESSAGE = "The user already exist";

    @Getter
    private Map<String, Object> paramMap;

    /**
     * Create duplicate exception for user
     *
     * @param paramMap parameters map of the duplicated user
     */
    public DuplicateUserException (Map<String,Object> paramMap){
        super(ERROR_MESSAGE, HttpStatus.CONFLICT, paramMap);
    }
}
