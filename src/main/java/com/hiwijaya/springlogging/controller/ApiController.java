package com.hiwijaya.springlogging.controller;

import com.hiwijaya.springlogging.model.LoginRequest;
import com.hiwijaya.springlogging.service.AuthService;
import com.hiwijaya.springlogging.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiController {

    @Autowired
    private AuthService authService;


    @GetMapping("/")
    public ResponseEntity<Object> index() {
        return ResponseHandler.createResponse("SPRING-LOGGING");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest body) {

        String token = authService.login(body);

        return ResponseHandler.createResponse(token);
    }

    @GetMapping("/book")
    public ResponseEntity<Object> getBook(){

        return ResponseHandler.createResponse("BOOK");
    }




}
