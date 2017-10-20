package com.jpUniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    private String index() {
        return "index";
    }

    @RequestMapping("/login")
    private String login() {
        return "login";
    }

    @RequestMapping("/register")
    private String register() {
        return "register";
    }

    @RequestMapping("/blank")
    private String blank() {
        return "blank";
    }

    @RequestMapping("/tables")
    public String table() {
        return "tables";
    }

    @RequestMapping("/navbar")
    public String navbar() {
        return "navbar";
    }

    @RequestMapping("/cards")
    public String card() {
        return "cards";
    }

    @RequestMapping("/charts")
    public String chart() {
        return "charts";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
