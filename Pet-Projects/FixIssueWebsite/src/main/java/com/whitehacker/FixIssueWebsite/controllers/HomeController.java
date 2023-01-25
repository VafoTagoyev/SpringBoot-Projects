package com.whitehacker.FixIssueWebsite.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String redirectToHome() {


        return "redirect:/home";
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Collection<? extends GrantedAuthority> role = new ArrayList<>();
//        role = principal.getAuthorities();
//
//        if (role.toString().equals("[ROLE_ADMIN]")){
//            return "redirect:/admin";
//        } else if (role.toString().equals("[ROLE_MODERATOR]")){
//            return "redirect:/moderator";
//        } else {
//            return "redirect:/user";
//        }
    }
}
