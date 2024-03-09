package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> getAllUsers();

    User findUserByFirstName(String firstName);

    void updateUser(User user);

    void removeUser(Long id);

    User findOneById(Long id);

    User findByEmail(String email);

    void create(User user);

    Optional<User> doesPersonExist(String emil);
}