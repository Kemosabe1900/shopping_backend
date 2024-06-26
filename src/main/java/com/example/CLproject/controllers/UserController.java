package com.example.CLproject.controllers;


import com.example.CLproject.models.User;
import com.example.CLproject.models.dtos.CreateUserDTO;
import com.example.CLproject.models.dtos.IncomingUserDTO;
import com.example.CLproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
        return ResponseEntity.ok(user);

    }
}
