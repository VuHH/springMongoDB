package com.mongodb.SpringMongodb.controller;

import com.mongodb.SpringMongodb.dto.AgeGroupCount;
import com.mongodb.SpringMongodb.entity.User;
import com.mongodb.SpringMongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{name}")
    public Optional<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/age")
    public List<User> getListUserByAgeGreater(@RequestParam int age) {
        return userService.getListUserByAgeGreater(age);
    }

    @GetMapping("/complex-query")
    public List<User> getUsersByComplexQuery(
            @RequestParam int minAge,
            @RequestParam String emailRegex) {
        return userService.getUsersByAgeAndEmail(minAge, emailRegex);
    }

    @GetMapping("/paged")
    public Page<User> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getUsersWithPagination(page, size);
    }

    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        return userService.findUsersByDynamicCriteria(name, minAge, maxAge);
    }

    @GetMapping("/age-group-count")
    public List<AgeGroupCount> countUsersByAgeGroup() {
        return userService.countUsersByAgeGroup();
    }

}
