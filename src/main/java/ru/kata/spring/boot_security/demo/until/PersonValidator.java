package ru.kata.spring.boot_security.demo.until;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;


import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final AdminService adminService;

    public PersonValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> existingPerson = adminService.doesPersonExist(user.getEmail());

        if (existingPerson.isPresent()  && user.getId() != existingPerson.get().getId()) {
            String errMsg = String.format("Email %s is not unique", existingPerson.get().getEmail());
            errors.rejectValue("email", "duplicate.email", errMsg);
        }

        if (user.getAge() != null && user.getAge() < 0) {
            errors.rejectValue("age", "negative.number", "Age must be a non-negative number");
        }

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            errors.rejectValue("firstName", "NotEmpty", "FirstName should not be empty");
        }

        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            errors.rejectValue("lastName", "NotEmpty", "LastName should not be empty");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            errors.rejectValue("roles", "NotEmpty", "The role should not be empty");
        }

    }


}
