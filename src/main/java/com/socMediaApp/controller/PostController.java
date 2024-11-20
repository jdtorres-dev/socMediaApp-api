package com.socMediaApp.controller;

import com.socMediaApp.model.Post;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.PostRepository;
import com.socMediaApp.repository.UserRespository;
import com.socMediaApp.service.PostService;
import com.socMediaApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return ResponseEntity.status(403).body("User is not authenticated.");
        }

        post.setUser(currentUser);
        post.setCreatedDate(LocalDateTime.now());

        Post createdPost = postRepository.save(post);

        return ResponseEntity.ok("Post created successfully with ID: " + createdPost.getId());
    }

}
