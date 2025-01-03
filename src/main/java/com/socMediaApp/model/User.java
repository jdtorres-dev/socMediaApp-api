package com.socMediaApp.model;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sma_user")
@Getter
@Setter
@NoArgsConstructor

public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String contact;

    @Column(nullable = true)
    private String interest;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String bio;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = true)
    private String imageUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.emptyList();
    }
   
}
