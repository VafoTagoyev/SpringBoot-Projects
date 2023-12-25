package uz.todoapp.web.service;

import uz.todoapp.web.model.User;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
}

