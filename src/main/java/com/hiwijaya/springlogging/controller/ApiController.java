package com.hiwijaya.springlogging.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("SPRING-LOGGING");
    }


}
