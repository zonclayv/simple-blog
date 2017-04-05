package by.haidash.blog.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by haidash on 04.04.17.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "index.html";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "/";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "/";
    }
}
