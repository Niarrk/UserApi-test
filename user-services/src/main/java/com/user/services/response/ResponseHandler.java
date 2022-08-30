package com.user.services.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 *  Response handler for controllers
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
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
     * @param params map of parameters
     * @return ResponseEntity with status, exception message and custom paramMap
     */
    public static ResponseEntity<Object> responseKOWithParams(HttpStatus status, Exception exception, Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", exception.getMessage());
        map.put("params", params);

        return new ResponseEntity<>(map,status);
    }
}