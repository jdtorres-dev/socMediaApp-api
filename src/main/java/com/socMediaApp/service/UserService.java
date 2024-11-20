package com.socMediaApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User updateUser(User user) {
        return userRepo.save(user);
    }

}
