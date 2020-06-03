package com.test.test.controller;

import com.test.test.dto.UserDto;
import com.test.test.entities.User;
import com.test.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController {
    @Autowired
    private UserDto userDto;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/users")
    public List<User> allUsers() {

        return userService.findAll();
    }

    @CrossOrigin
    @GetMapping("/users/count")
    public Long count() {

        return userService.count();
    }

    @CrossOrigin
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable String id) {

        Long userId = Long.parseLong(id);
        userService.deleteById(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("users/add")
    public @ResponseBody String add(@RequestBody User user){
        userService.addUser(user);
        return "Saved";
   }

    @CrossOrigin
    @PostMapping("/users/login")
    public @ResponseBody String login(@RequestBody UserDto user){
        String feedback = userService.login(user);
        return feedback;
    }
}