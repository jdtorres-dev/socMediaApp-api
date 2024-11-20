package com.socMediaApp.service;

import com.socMediaApp.model.Post;
import com.socMediaApp.repository.PostRepository;
import com.socMediaApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }
}
