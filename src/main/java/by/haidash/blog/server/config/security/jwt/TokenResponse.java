package by.haidash.blog.server.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by haidash on 04.04.17.
 */
@Data
@AllArgsConstructor
public class TokenResponse {
    private final String token;
}
