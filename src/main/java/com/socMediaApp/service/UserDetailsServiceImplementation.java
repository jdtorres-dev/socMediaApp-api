package com.socMediaApp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.User;
import com.socMediaApp.repository.UserRespository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{

   private final UserRespository userRepo;

   public UserDetailsServiceImplementation(UserRespository userRepo) {
    this.userRepo = userRepo;
   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return (UserDetails) user;
    }



}
