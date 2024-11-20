package com.socMediaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socMediaApp.model.User;


public interface UserRespository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    
}
