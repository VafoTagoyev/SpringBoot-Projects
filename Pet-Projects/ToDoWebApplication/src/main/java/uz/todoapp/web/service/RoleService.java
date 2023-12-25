package uz.todoapp.web.service;

import uz.todoapp.web.model.Role;

public interface RoleService {
    Role saveRole(Role role);
    Role getRoleByName(String name);
}

