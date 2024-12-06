package com.socMediaApp.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.socMediaApp.model.User;

@Service
public class CommentUserDtoMapper implements Function<User, CommentUserDto>{

    @Override
    public CommentUserDto apply(User user) {
       return new CommentUserDto(
        user.getId(), 
        user.getName(), 
        user.getUsername(), 
        user.getImageUrl(), 
        user.getEmail());
    
    }
}
