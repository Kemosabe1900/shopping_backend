package com.example.CLproject.controllers;


import com.example.CLproject.models.dtos.CreateUserDTO;
import com.example.CLproject.models.dtos.IncomingUserDTO;
import com.example.CLproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<Object>createUser(@RequestBody CreateUserDTO input) {
        return userService.createUser(input);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody IncomingUserDTO input ) {
        return userService.login(input);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<Object> createAdmin(@RequestBody CreateUserDTO input) {
        return userService.createAdmin(input);
    }
}
