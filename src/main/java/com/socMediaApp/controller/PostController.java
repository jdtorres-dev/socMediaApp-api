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
import java.util.Optional;


@RestController
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        Optional <User> currentUser = userService.getCurrentUser(post.getUser().getId());

       // Post newPost = new Post();

       //if (currentUser.isEmpty()) {
          // return ResponseEntity.status(403).body("User is not authenticated.");
          //  newPost.setUser(currentUser.get());
       // }

        post.setUser(currentUser.get());
        post.setCreatedDate(LocalDateTime.now());
       // System.out.println(post.toString());
        Post createdPost = postRepository.save(post);

        return ResponseEntity.ok("Post created successfully with ID: " + createdPost.getId());
    }

}
