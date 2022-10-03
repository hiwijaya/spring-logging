package com.hiwijaya.springlogging.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    private static Map<String, Object> body;


    private static void initBody(Object data, HttpStatus code, String message){
        Map<String, Object> status = new HashMap<>();
        status.put("code", code.value());
        status.put("message", message);

        body = new HashMap<>();
        body.put("data", data);
        body.put("status", status);
    }

    public static Map<String, Object> getBody(){
        return body;
    }

    public static ResponseEntity<Object> createResponse(Object data, HttpStatus code, String message){
        initBody(data, code, message);

        return new ResponseEntity<Object>(body, code);
    }

    public static ResponseEntity<Object> createResponse(HttpStatus code, String message){
        return createResponse(null, code, message);
    }

    public static ResponseEntity<Object> createResponse(Object data){
        initBody(data, HttpStatus.OK, "Success");

        return createResponse(data, HttpStatus.OK, "Success");
    }

    public static ResponseEntity<Object> ok() {
        return createResponse(HttpStatus.OK,"Success");
    }

}
