package com.socMediaApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.Comment;
import com.socMediaApp.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    public Optional<Comment> getCurrentComment(Long id) {
        return commentRepo.findById(id);
    }

}
