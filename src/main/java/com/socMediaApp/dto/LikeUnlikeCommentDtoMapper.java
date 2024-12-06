package com.socMediaApp.dto;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.LikeUnlikeComment;

@Service
public class LikeUnlikeCommentDtoMapper implements Function<LikeUnlikeComment, LikeUnlikeCommentDto>{
    @Autowired
    private CommentUserDtoMapper commentUserDtoMapper;

    @Autowired
    private CommentDTOMapper commentDtoMapper;

    @Override
    public LikeUnlikeCommentDto apply(LikeUnlikeComment likeUnlikeComment) {
       return new LikeUnlikeCommentDto(
        likeUnlikeComment.getId(), 
        likeUnlikeComment.getIsLike(), 
        commentUserDtoMapper.apply(likeUnlikeComment.getUser()), 
        commentDtoMapper.apply(likeUnlikeComment.getComment()), 
        likeUnlikeComment.getCreatedDate());
    }


}
