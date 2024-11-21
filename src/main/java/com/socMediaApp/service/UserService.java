package com.socMediaApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.UserRespository;

@Service
public class UserService {

    @Autowired
    UserRespository userRepo;

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public Optional<User>  getCurrentUser(Long id) { return userRepo.findById(id); }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

}
