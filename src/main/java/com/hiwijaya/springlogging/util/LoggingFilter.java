package com.hiwijaya.springlogging.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = this.getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = this.getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        this.writeLog(
                getClientIp(request),
                request.getMethod(),
                getRequestUriWithParams(request),
                requestBody,
                String.valueOf(response.getStatus()),
                timeTaken);

        responseWrapper.copyBodyToResponse();
    }


    private void writeLog(
            String ip,
            String method,
            String uri,
            String requestBody,
            String responseStatus,
            long timeTaken){

        JSONObject info = new JSONObject();
        info.put("IP_ADDRESS", ip);
        info.put("METHOD", method);
        info.put("URI", uri);
        info.put("BODY", safelyJson(requestBody));
        info.put("RESPONSE_STATUS", responseStatus);
        info.put("TIME_TAKEN", timeTaken);


        log.info("API request. {}", info.toString());

    }

    public Object safelyJson(String data) {
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            try {
                return new JSONArray(data);
            } catch (JSONException ne) {
                return "";
            }
        }
    }






    private String getRequestUriWithParams(HttpServletRequest request){

        String params = request.getQueryString();

        if(params != null){
            return  request.getRequestURI() + "?" + params;
        }

        return request.getRequestURI();
    }

    private String getStringValue(byte[] contentAsByteArray, String encoding){

        try{
            return new String(contentAsByteArray, 0, contentAsByteArray.length, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getClientIp(HttpServletRequest request) {

        final String LOCALHOST_IPV4 = "127.0.0.1";
        final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

        String ipAddress = request.getHeader("X-Forwarded-For");

        if(!StringUtils.hasLength(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if(!StringUtils.hasLength(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if(!StringUtils.hasLength(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if(StringUtils.hasLength(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

}
