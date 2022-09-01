package com.user.controller.response;

import com.fasterxml.jackson.core.JsonParseException;
import com.user.business.exception.DuplicateUserException;
import com.user.business.exception.RuntimeExceptionExtender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 *  Response handler for controllers
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */

@ControllerAdvice(value = "com.user.controller")
public class ResponseHandler {

    /**
     * Format responseOk
     *
     * @param status http status
     * @param response
     * @return ResponseEntity with status and json data
     */
    public static ResponseEntity<Object> responseOk(HttpStatus status, Object response) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("data", response);

        return new ResponseEntity<>(map,status);
    }

    /**
     * Format error response with code status and paramsMap if complete
     *
     * @param exception thrown exception
     * @return ResponseEntity with status and exception message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> responseKO(final RuntimeExceptionExtender exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", exception.getStatus().value());
        map.put("message", exception.getMessage());
        if(exception.getParamMap() != null){
            map.put("params", exception.getParamMap());
        }

        return new ResponseEntity<>(map,exception.getStatus());
    }

    /**
     * Format error response for a bad format json
     *
     * @param exception thrown JsonParseException
     * @return ResponseEntity with status and exception message
     */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Object> responseJsonParserException(JsonParseException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("message", exception.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}