package com.socMediaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socMediaApp.dto.CheckUserErrorResponseDTO;
import com.socMediaApp.dto.CheckUserRequestDTO;
import com.socMediaApp.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;


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
    
    @PostMapping("/check-user")
    public ResponseEntity<?> checkUser(@RequestBody CheckUserRequestDTO checkUserRequest) {
        try {
            boolean usernameExists = userService.existsByUsername(checkUserRequest.getUsername());
            boolean emailExists = userService.existsByEmail(checkUserRequest.getEmail());
    
            if (usernameExists && emailExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CheckUserErrorResponseDTO("Both username and email are already taken.", "username, email"));
            }
    
            if (usernameExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CheckUserErrorResponseDTO("Username is already taken.", "username"));
            }
    
            if (emailExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CheckUserErrorResponseDTO("Email already registered.", "email"));
            }
    
            return ResponseEntity.ok("Username and email are available.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }
    

}
