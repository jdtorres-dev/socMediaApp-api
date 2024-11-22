package com.socMediaApp.dto;

import com.socMediaApp.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostUserDTOMapper implements Function<User, PostUserDTO> {
    @Override
    public PostUserDTO apply(User user) {
        return new PostUserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getImageUrl(),
                user.getEmail()
        );
    }
}
