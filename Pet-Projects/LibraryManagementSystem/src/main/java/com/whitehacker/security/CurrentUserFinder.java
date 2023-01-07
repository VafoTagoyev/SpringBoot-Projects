package com.whitehacker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.whitehacker.entities.User;
import com.whitehacker.services.UserService;

@Service
public class CurrentUserFinder {

    @Autowired
    UserService userService;

    public long getCurrentUserId() {
        UserDetails details = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        long userId = -1;
        for (User user : userService.findAll()) {
            if (user.getUserName().equals(username)) {
                userId = user.getUserId();
                break;
            }
        }
        return userId;
    }

    public User getCurrentUser() {
        User currentUser = userService.findById(getCurrentUserId());
        return currentUser;
    }
}
