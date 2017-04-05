package by.haidash.blog.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.haidash.blog.server.config.security.jwt.AuthenticationUser;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    private AuthenticationUser currentUser() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }

        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (AuthenticationUser) auth.getPrincipal();
    }

    @Bean
    public AuthenticationUser auditor() {
        return currentUser();
    }
}
