package com.whitehacker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.whitehacker.entities.User;
import com.whitehacker.services.UserService;

@Controller
public class SecurityController {

    @Autowired
    BCryptPasswordEncoder pwEncoder;

    @Autowired
    UserService accService;

    @GetMapping(value="/login")
    public String login() {
        return "security/login";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "security/logout";
    }

    @GetMapping(value="/register")
    public String register(Model model) {
        model.addAttribute("newAccount", new User());
        return "security/register";
    }

    @PostMapping(value="/register/save")
    public String saveNewAccount(User account) {
        account.setPassword(pwEncoder.encode(account.getPassword()));
        accService.save(account);
        return "redirect:/register/accountcreated";
    }

    @GetMapping(value="/register/accountcreated")
    public String accountCreated() {
        return "security/account-created";
    }
}
