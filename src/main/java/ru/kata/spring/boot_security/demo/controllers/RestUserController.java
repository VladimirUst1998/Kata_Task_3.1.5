package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.PeopleService;


import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    private final PeopleService peopleService;

    public RestUserController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping("/showAccount")
    public ResponseEntity<User> showUserAccount(Principal principal) {
        User person = peopleService.findUserByEmail(principal.getName()).get();
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}