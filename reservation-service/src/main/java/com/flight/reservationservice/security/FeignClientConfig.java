package com.flight.reservationservice.security;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // 🔥 Sauvegarde le contexte de sécurité actuel
            SecurityContext context = SecurityContextHolder.getContext();
            if (context.getAuthentication() == null) {
                System.out.println("❌ Aucune authentification trouvée !");
                return;
            }

            // ✅ Exécuter Feign dans le contexte de sécurité
            Runnable securedFeignCall = SecurityContextRunnable.create(() -> {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
                    String token = jwtAuthentication.getToken().getTokenValue();
                    System.out.println("🚀 Token envoyé dans Feign : " + token);
                    requestTemplate.header("Authorization", "Bearer " + token);
                } else {
                    System.out.println("❌ L'authentification n'est pas un JWT !");
                }
            }, context);

            securedFeignCall.run(); // Exécuter dans le contexte sécurisé
        };
    }

    /**
     * Utilitaire pour exécuter une tâche dans le contexte de sécurité actuel
     */
    public static class SecurityContextRunnable implements Runnable {
        private final Runnable delegate;
        private final SecurityContext securityContext;

        private SecurityContextRunnable(Runnable delegate, SecurityContext securityContext) {
            this.delegate = delegate;
            this.securityContext = securityContext;
        }

        public static Runnable create(Runnable runnable, SecurityContext securityContext) {
            return new SecurityContextRunnable(runnable, securityContext);
        }

        @Override
        public void run() {
            SecurityContext previousContext = SecurityContextHolder.getContext();
            try {
                SecurityContextHolder.setContext(securityContext); // 🔄 Transmettre le contexte
                delegate.run(); // ✅ Exécuter la tâche avec le bon contexte
            } finally {
                SecurityContextHolder.setContext(previousContext); // 🔄 Restaurer l'ancien contexte
            }
        }
    }
}
