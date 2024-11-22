package com.socMediaApp.dto;

import com.socMediaApp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostDTOMapper  implements Function<Post, PostDTO> {

    @Autowired
    private PostUserDTOMapper postUserDTOMapper;

    @Override
    public PostDTO apply(Post post) {

        return new PostDTO(
                post.getId(),
                post.getCreatedDate(),
                post.getBody(),
                post.getImageUrl(),
                postUserDTOMapper.apply(post.getUser())
        );
    }


}
