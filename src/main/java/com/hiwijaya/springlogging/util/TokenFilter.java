package com.hiwijaya.springlogging.util;

import com.hiwijaya.springlogging.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    private final Set<String> PUBLIC_URLS = new HashSet<>(Arrays.asList(
            "/", "/login"
    ));


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

        return PUBLIC_URLS.stream().anyMatch(p -> new AntPathMatcher().match(p, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHeader(request);

        if(!authService.validateToken(token)){

            // WARN: Filters happens before controllers are even resolved so exceptions thrown from filters can't be caught by a Controller Advice.
            // https://stackoverflow.com/questions/17715921/exception-handling-for-filter-in-spring

            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "You have no authorize");
        }

        filterChain.doFilter(request, response);

    }

    private String getTokenFromHeader(HttpServletRequest request){

        return request.getHeader("x-token");
    }

}
