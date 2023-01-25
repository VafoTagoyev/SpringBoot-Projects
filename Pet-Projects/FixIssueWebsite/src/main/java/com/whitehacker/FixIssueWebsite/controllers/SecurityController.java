package com.whitehacker.FixIssueWebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(value="/login")
    public String login() {
        return "security/login";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "security/logout";
    }

    // TODO : register, register/save, register/accountcreated.
}
