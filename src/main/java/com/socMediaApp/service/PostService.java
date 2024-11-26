package com.socMediaApp.service;

import com.socMediaApp.dto.PostDTO;
import com.socMediaApp.model.Post;
import com.socMediaApp.repository.PostRepository;
import com.socMediaApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    PostRepository postRepo;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> getCurrentPost(Long id) { return postRepo.findById(id); }


}
