package com.example.surepayservice.security;

import com.example.surepayservice.domains.AppResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityAuthenticationEntrypoint implements AuthenticationEntryPoint {
    ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        AppResponse appResponse = new AppResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(),
                ex.getMessage(), null, ex.getMessage());
        String json = objectMapper.writeValueAsString(appResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
