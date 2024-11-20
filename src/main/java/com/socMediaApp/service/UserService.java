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

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
    public User updateUser(User user) {
        return userRepo.save(user);
    }

}
