package by.haidash.blog.server.config.security.jwt;

import by.haidash.blog.server.model.entity.User;

/**
 * Created by haidash on 04.04.17.
 */
public final class AuthenticationUserFactory {

    private AuthenticationUserFactory() {
    }

    public static AuthenticationUser create(final User user) {
        return new AuthenticationUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getCreatedDate(),
                user.getAvatar()
        );
    }
}