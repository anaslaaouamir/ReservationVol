package com.flight.reservationservice.security;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                String token = extractJwtTokenFromRequest();

                if (StringUtils.hasText(token)) {
                    System.out.println("🚀 Token JWT extrait et envoyé dans Feign : " + token);
                    requestTemplate.header("Authorization", "Bearer " + token);
                } else {
                    System.out.println("❌ Aucun token JWT trouvé !");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Erreur lors de la récupération du token JWT pour Feign : " + e.getMessage());
            }
        };
    }
    private String extractJwtTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authorizationHeader = request.getHeader("Authorization");
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7); // Supprime "Bearer " pour ne garder que le token
            }
        }
        return null;
    }
}
