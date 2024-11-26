package com.socMediaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socMediaApp.model.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @SuppressWarnings("null")
    Optional<User> findById(Long id);

    
}
