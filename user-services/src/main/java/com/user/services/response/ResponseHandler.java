package com.user.services.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 *  Response handler
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
public class ResponseHandler {

    /**
     * Format responseOk
     *
     * @param status
     * @param response
     * @return
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
     * @param status status
     * @param exception exception
     * @return
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
     * @param status status
     * @param exception exception
     * @param params map of parameters
     * @return
     */
    public static ResponseEntity<Object> responseKOWithParams(HttpStatus status, Exception exception, Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", exception.getMessage());
        map.put("params", params);

        return new ResponseEntity<>(map,status);
    }
}