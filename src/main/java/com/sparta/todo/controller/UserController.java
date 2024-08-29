package com.sparta.todo.controller;

import com.sparta.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password) {
        return userService.registerUser(username, password);
    }
}