package com.user.controller.response;

import com.user.business.exception.DuplicateUserException;
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
     * Format error response
     *
     * @param status http status
     * @param exception thrown exception
     * @return ResponseEntity with status and exception message
     */
    @ExceptionHandler(RuntimeException.class)
    public static ResponseEntity<Object> responseKO(HttpStatus status, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", exception.getMessage());

        return new ResponseEntity<>(map,status);
    }

    /**
     * Format error response with paramMap
     *
     * @param status http status
     * @param exception thrown exception
     * @return ResponseEntity with status, exception message and custom paramMap
     */
    @ExceptionHandler(DuplicateUserException.class)
    public static ResponseEntity<Object> responseKOWithParams(HttpStatus status, DuplicateUserException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", exception.getMessage());
        map.put("params", exception.getParamMap());

        return new ResponseEntity<>(map,status);
    }
}