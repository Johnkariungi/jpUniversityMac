package com.jpUniversity.controller;

import com.jpUniversity.domain.User;
import com.jpUniversity.domain.security.PasswordResetToken;
import com.jpUniversity.service.UserService;
import com.jpUniversity.service.impl.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HomeController {

    @Autowired /*auto wire user service for token*/
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping("/")
    private String index() {
        return "index";
    }

    @RequestMapping("/login")
    private String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }

    @RequestMapping("/register")
    private String register(
            Locale locale,
            @RequestParam("token") String token,
            Model model) {

        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if(passToken == null) {
            String message = "Invalid token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest"; /*error page for those trying to hijack the page*/
        }
        /*code block below is to set the current session to that user*/

        /*otherwise get user from the token in the database*/
        User user = passToken.getUser();
        /*assign username*/
        String username = user.getUsername();
        /*get user details form user security services*/
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        /*define authentication environment*/
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        /*retrieve current security context from the current and set authentication to the current user*/
        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("classActiveEdit", true);
        return "myProfile";
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
    public String forgotPassword(Model model) {
        model.addAttribute("classActiveForgetPassword", true);
        return "forgot-password";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
