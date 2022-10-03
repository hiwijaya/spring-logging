package com.hiwijaya.springlogging.service;

import com.hiwijaya.springlogging.model.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AuthService {

    private static final String TOKEN = "0123456789";

    public String login(LoginRequest req) {


        if("admin".equals(req.getUsername()) && "secr3t".equals(req.getPassword())){
            return TOKEN;
        }

        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "credential not match");
    }

    public boolean validateToken(String token){

        return TOKEN.equals(token);
    }


}
