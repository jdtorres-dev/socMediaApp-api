package com.socMediaApp.dto;

import com.socMediaApp.model.Comment;
import com.socMediaApp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Autowired
    private PostUserDTOMapper postUserDTOMapper;

    @Autowired
    private PostDTOMapper postDTOMapper;

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getCommentBody(),
                comment.getCreatedDate(),
                postUserDTOMapper.apply(comment.getUser()),
                postDTOMapper.apply(comment.getPost())
        );
    }
    }
