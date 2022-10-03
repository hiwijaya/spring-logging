package com.hiwijaya.springlogging.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(value = HttpClientErrorException.class)
    private ResponseEntity<Object> handleConstraintViolation(HttpClientErrorException ex, WebRequest request){


        return ResponseHandler.createResponse(ex.getStatusCode(), ex.getMessage());
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body, HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {

        ex.printStackTrace();

        return ResponseHandler.createResponse(status, status.getReasonPhrase());
    }

}
