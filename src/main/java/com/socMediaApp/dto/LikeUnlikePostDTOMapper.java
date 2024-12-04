package com.socMediaApp.dto;

import com.socMediaApp.model.Comment;
import com.socMediaApp.model.LikeUnlikePost;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LikeUnlikePostDTOMapper implements Function<LikeUnlikePost, LikeUnlikePostDTO> {
    @Autowired
    private PostUserDTOMapper postUserDTOMapper;

    @Autowired
    private PostDTOMapper postDTOMapper;

    @Override
    public LikeUnlikePostDTO apply(LikeUnlikePost likeUnlikePost) {
        return new LikeUnlikePostDTO(
                likeUnlikePost.getId(),
                likeUnlikePost.getIsLike(),
                postUserDTOMapper.apply(likeUnlikePost.getUser()),
                postDTOMapper.apply(likeUnlikePost.getPost()),
                likeUnlikePost.getCreatedDate()
        );
    }
}
