package by.haidash.blog.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import by.haidash.blog.server.config.security.SecurityConfig;
import by.haidash.blog.server.model.entity.User;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<User> auditor() {
        return SecurityConfig::currentUser;
    }
}
