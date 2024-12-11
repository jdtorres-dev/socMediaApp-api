package com.socMediaApp.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socMediaApp.model.User;
import com.socMediaApp.repository.UserRespository;
import com.socMediaApp.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRespository userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam(required = false) String username) {
        try {
            boolean usernameExists = userService.existsByUsername(username);

            if(!usernameExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Username is valid.");
            } 
    
            return ResponseEntity.ok("Username already taken");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam(required = false) String email) {
        try {
            boolean emailExists = userService.existsByEmail(email);

            if(!emailExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Email is valid.");
            } 
    
            return ResponseEntity.ok("Email already registered.");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {

        Optional<User> result = userRepo.findById(id); 

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("User does not exist.");
        }

        try {
            User existing = result.get();
        
            BeanUtils.copyProperties(user, existing, "id", "password"); 

            existing.setName(user.getName());
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setContact(user.getContact());
            existing.setInterest(user.getInterest());
            existing.setAddress(user.getAddress());
            existing.setBio(user.getBio());
            existing.setImageUrl(user.getImageUrl());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                existing.setPassword(hashedPassword);
            } else {
                existing.setPassword(existing.getPassword());
            }
            
            userService.updateUser(existing);

            return ResponseEntity.ok("User update successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred while processing the request.");
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        Optional<User> result = userRepo.findById(id); 
        
        try {

            if(result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No record found");
            }

            return ResponseEntity.ok(result);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }



}
