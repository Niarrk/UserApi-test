package com.user.business.exception;

import com.user.data.dto.UserDto;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * DuplicateUserException
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class DuplicateUserException extends RuntimeException{
    private final static String ERROR_MESSAGE = "The user already exist";

    @Getter
    private Map<String, Object> paramMap;

    /**
     * Create duplicate exception for user
     *
     * @param user 
     */
    public DuplicateUserException (UserDto user){
        super(ERROR_MESSAGE);
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("country", user.getCountry());
        params.put("birthdate", user.getBirthdate());

        paramMap = params;
    }
}
