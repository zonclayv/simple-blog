package by.haidash.blog.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import by.haidash.blog.server.config.security.SecurityConfig;
import by.haidash.blog.server.model.entity.User;

/**
 * Created by haidash on 28.02.17.
 */
@RestController("/auth")
@RequestMapping(value = "/auth")
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public User login() {
        return SecurityConfig.currentUser();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
