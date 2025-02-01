package com.flight.offrevolservice.security;

import feign.RequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // Extract the token from the authentication object
                String token = (String) authentication.getCredentials(); // Assuming the token is stored as credentials
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
