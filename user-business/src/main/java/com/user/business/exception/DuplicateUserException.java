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
     * @param user 
     */
    public DuplicateUserException (UserDto user){
        super(ERROR_MESSAGE, HttpStatus.CONFLICT,
                Map.of("name", user.getName(),
                        "country", user.getCountry(),
                        "birthdate", user.getBirthdate()));
    }
}
