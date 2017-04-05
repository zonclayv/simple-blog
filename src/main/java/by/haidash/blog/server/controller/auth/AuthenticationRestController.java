package by.haidash.blog.server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.haidash.blog.server.config.security.jwt.AuthenticationRequest;
import by.haidash.blog.server.config.security.jwt.TokenResponse;
import by.haidash.blog.server.config.security.jwt.TokenUtil;
import by.haidash.blog.server.config.security.jwt.AuthenticationUser;

/**
 * Created by haidash on 04.04.17.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${authentication.header}")
    public String authorizationHeader;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(final @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        doAuthenticate(authenticationRequest);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    private void doAuthenticate(final AuthenticationRequest authenticationRequest) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );

        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(final HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(final HttpServletRequest request) {
        final String token = request.getHeader(authorizationHeader);
        if (tokenUtil.canTokenBeRefreshed(token)) {
            return ResponseEntity.ok(new TokenResponse(tokenUtil.refreshToken(token)));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public AuthenticationUser getAuthenticatedUser(final HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = tokenUtil.getUsernameFromToken(token);
        AuthenticationUser user = (AuthenticationUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
