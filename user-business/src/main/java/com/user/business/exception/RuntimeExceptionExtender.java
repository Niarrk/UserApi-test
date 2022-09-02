package com.user.business.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * RuntimeExceptionExtender
 * Override RuntimeExpection to add Httpstatus and map of parameters
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
public class RuntimeExceptionExtender extends RuntimeException{

    @Getter
    private HttpStatus status;

    @Getter
    private Map<String, Object> params;

    /**
     * Generate exception with message
     * @param message message
     */
    public RuntimeExceptionExtender (final String message, final HttpStatus status){
        super(message);
        this.status = status;
    }

    public RuntimeExceptionExtender (final String message, final HttpStatus status, final Map<String, Object> params){
        super(message);
        this.params = params;
        this.status = status;
    }
}
