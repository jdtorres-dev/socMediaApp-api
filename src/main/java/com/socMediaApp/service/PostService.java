package com.socMediaApp.service;

import com.socMediaApp.model.Post;
import com.socMediaApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
 
    @Autowired
    PostRepository postRepo;

    public Optional<Post> getCurrentPost(Long id) { 
        return postRepo.findById(id); 
    }

    public Post updatePost(Post post) {
        return postRepo.save(post);
    }

}
