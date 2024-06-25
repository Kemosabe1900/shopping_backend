package com.example.CLproject.services;

import com.example.CLproject.models.Cart;
import com.example.CLproject.models.User;
import com.example.CLproject.models.dtos.CartDTO;
import com.example.CLproject.models.dtos.CreateUserDTO;
import com.example.CLproject.daos.UserRepository;
import com.example.CLproject.models.dtos.IncomingUserDTO;
import com.example.CLproject.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartService cartService;

    private final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$";
    private final Pattern pat = Pattern.compile(EMAIL_REGEX);



    public boolean isValidEmail(String email) {
        Matcher matcher = pat.matcher(email);
        return matcher.matches();
    }

    private User convertUserFromCreateUserDTO(CreateUserDTO input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(User.Role.USER);
        return user;
    }



    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser( CreateUserDTO input) {
        System.out.println(input.toString());
        try{

            if (input.getUsername() == null || input.getUsername().isEmpty() ||
                    input.getPassword() == null || input.getPassword().isEmpty() ||
                    input.getFirstName() == null || input.getFirstName().isEmpty() ||
                    input.getLastName() == null || input.getLastName().isEmpty() ||
                    input.getEmail() == null || input.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Please fill out all fields");
            }

            if (!isValidEmail(input.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)//400
                        .body("Invalid email: User email format incorrect");
            }

            if (userDAO.existsByUsername(input.getUsername())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)//409
                        .body("Username already exists");
            }
            User user = convertUserFromCreateUserDTO(input);
            userDAO.save(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED) //201
                    .body("User added successfully");


        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Failed to add user: User information is invalid");
        }

    }

    public ResponseEntity<Object> login(IncomingUserDTO input) {
        try {
            if (input.getUsername() == null || input.getUsername().isEmpty() ||
                    input.getPassword() == null || input.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Username and password must not be empty");
            }

            User user = userDAO.findByUsername(input.getUsername());
            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login failed: Invalid username or password");
            }
            if(!passwordEncoder.matches(input.getPassword(), user.getPassword())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login failed: Invalid username or password");
            }
            final String jwt = jwtUtil.generateAccessToken(user);
            CartDTO cartDTO = cartService.getCartItemsForUser(user);

            return ResponseEntity.ok(user.getUsername() + " Logged in successfully" );


        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) //401
                    .body("Failed to Login: " + e.getMessage());
        }

    }

    public ResponseEntity<Object> createAdmin(CreateUserDTO input) {
        try {
            if (input.getUsername() == null || input.getUsername().isEmpty() ||
                    input.getPassword() == null || input.getPassword().isEmpty() ||
                    input.getFirstName() == null || input.getFirstName().isEmpty() ||
                    input.getLastName() == null || input.getLastName().isEmpty() ||
                    input.getEmail() == null || input.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Please fill out all fields");
            }

            if (!isValidEmail(input.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)//400
                        .body("Invalid email: User email format incorrect");
            }

            if (userDAO.existsByUsername(input.getUsername())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)//409
                        .body("Username already exists");
            }

            User admin = convertUserFromCreateUserDTO(input);
            admin.setRole(User.Role.ADMIN);  // Set role to ADMIN
            userDAO.save(admin);

            return ResponseEntity
                    .status(HttpStatus.CREATED) //201
                    .body("Admin added successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Failed to add admin: User information is invalid");
        }
    }


    public ResponseEntity<Object> adminLogin(IncomingUserDTO input){
        try{
            if(input.getUsername() == null || input.getUsername().isEmpty() ||
                    input.getPassword() == null || input.getPassword().isEmpty()){
                return ResponseEntity.badRequest().body("Username and password must not be empty");
            }

            User user = userDAO.findByUsername(input.getUsername());
            if(user == null || user.getRole() != User.Role.ADMIN){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login failed: Invalid username or password");
            }
            if(!passwordEncoder.matches(input.getPassword(), user.getPassword())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login failed: Invalid username or password");
            }
            final String jwt = jwtUtil.generateAccessToken(user);
            return ResponseEntity.ok(user.getUsername() + " Logged in successfully" );
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) //401
                    .body("Failed to Login: " + e.getMessage());
        }
    }

//    public void createAdminUser(){
//        User admin = new User();
//        admin.setUsername("nahom");
//        admin.setPassword(passwordEncoder.encode("nahom123"));
//        admin.setFirstName("nahom");
//        admin.setLastName("n");
//        admin.setEmail("nahom.n@gmail.com");
//        admin.setRole(User.Role.ADMIN);
//
//        userDAO.save(admin);
//    }

    public String getBCryptHashedPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

}
