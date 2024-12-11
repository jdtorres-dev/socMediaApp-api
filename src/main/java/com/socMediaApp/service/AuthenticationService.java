package com.socMediaApp.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.AuthenticationResponse;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.UserRespository;

@Service
public class AuthenticationService {

    private final UserRespository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRespository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setImageUrl(request.getImageUrl());
        user.setInterest(request.getInterest());
        user.setContact(request.getContact());
        user.setAddress(request.getAddress());
        user.setBio(request.getBio());
        
        user = userRepo.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        String loginIdentifier = request.getUsername();
    
        Optional<User> userOptional = loginIdentifier.contains("@")
            ? userRepo.findByEmail(loginIdentifier)  
            : userRepo.findByUsername(loginIdentifier); 
        
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())  
        );
    
        String token = jwtService.generateToken(user);
    
        return new AuthenticationResponse(token);
    }
    

}
