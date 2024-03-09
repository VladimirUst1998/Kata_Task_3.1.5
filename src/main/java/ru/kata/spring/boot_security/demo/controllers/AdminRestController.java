package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.until.PersonValidator;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    public AdminRestController(AdminService adminService, RoleService roleService, PersonValidator personValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }


    @GetMapping("/showAccount")
    public ResponseEntity<User> showInfoUser(Principal principal) {
        System.out.println(principal.getName());
        return new ResponseEntity<>(adminService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User person = adminService.findOneById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.findOneById(id).getRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid User newUser, BindingResult bindingResult) {

        personValidator.validate(newUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        adminService.create(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid User updatedPerson, BindingResult bindingResult) {

        personValidator.validate(updatedPerson, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        adminService.updateUser(updatedPerson);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.removeUser(id);
        return new ResponseEntity<>("User with id " + id + " was deleted", HttpStatus.OK);
    }
}
