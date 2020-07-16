package com.blog.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    //   All users
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    //   All users
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //    Only authenticated users
    @GetMapping("/hello")
    public String getHello() {
        return "hello";
    }


}
