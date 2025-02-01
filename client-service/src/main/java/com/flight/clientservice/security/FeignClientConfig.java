package com.flight.clientservice.security;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof PreAuthenticatedAuthenticationToken) {
                // Pour votre cas, le token est dans le principal, pas dans les credentials
                String token = extractTokenFromRequest();
                if (token != null) {
                    requestTemplate.header("Authorization", "Bearer " + token);
                }
            }
        };
    }

    private String extractTokenFromRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }
}
