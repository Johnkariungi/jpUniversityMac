package com.jpUniversity.controller;

import com.jpUniversity.domain.User;
import com.jpUniversity.domain.security.PasswordResetToken;
import com.jpUniversity.domain.security.Role;
import com.jpUniversity.domain.security.UserRole;
import com.jpUniversity.service.UserService;
import com.jpUniversity.service.impl.UserSecurityService;
import com.jpUniversity.utility.MailConstructor;
import com.jpUniversity.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

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

    @RequestMapping("/forgetPassword")
    public String forgetPassword(
            HttpServletRequest request,
            @ModelAttribute("email") String email,
            Model model
    ) {

        model.addAttribute("classActiveForgetPassword", true);
        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user); /*simply save user, not dealing with roles*/

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);
        model.addAttribute("forgetPasswordEmailSent" , true);

        return "myAccount";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model
        ) throws Exception {
            model.addAttribute("classActiveNewAccount", true);
            model.addAttribute("email", userEmail);
            model.addAttribute("username", username);

            /*check if there no duplicates*/
        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "myAccount";
        }

        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);

            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);
        model.addAttribute("emailSent" , true);

        return "myAccount";
    }

    @RequestMapping("/newUser")
    private String newUser(Locale locale, @RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if(passToken == null) {
            String message = "Invalid token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest"; /*error page for those trying to hijack the page*/
        }
        /*code block below is to set the current session to that user
        otherwise get user from the token in the database*/
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


        model.addAttribute("user", user);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    /*@RequestMapping("/blank")
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

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
        HttpServletRequest request,
        @ModelAttribute("email") String userEmail,
        @ModelAttribute("username") String username,
        Model model
        ) throws Exception {
            model.addAttribute("classActiveNewAccount", true);
            model.addAttribute("email", userEmail);
            model.addAttribute("username", username);

            *//*check if there no duplicates*//*
            if (userService.findByUsername(username) != null) {
                model.addAttribute("usernameExists", true);

                return "myAccount";
            }

            if (userService.findByEmail(userEmail) != null) {
                model.addAttribute("emailExists", true);

                return "myAccount";
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(userEmail);

            String password = SecurityUtility.randomPassword();

            String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
            user.setPassword(encryptedPassword);

            Role role = new Role();
            role.setRoleId(1);
            role.setName("ROLE_USER");

            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, role));
            userService.createUser(user, userRoles);

            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);

            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

            mailSender.send(email);
            model.addAttribute("emailSent" , true);

            return "myAccount";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }*/
}
