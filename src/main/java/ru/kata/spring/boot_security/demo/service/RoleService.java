package ru.kata.spring.boot_security.demo.service;





import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();

    Role findByName(String name);

    void saveRole(Role role);

}
