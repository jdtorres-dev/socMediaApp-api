package com.socMediaApp.controller;

import com.socMediaApp.dto.PostDTO;
import com.socMediaApp.dto.PostDTOMapper;
import com.socMediaApp.model.Post;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.PostRepository;
import com.socMediaApp.repository.UserRespository;
import com.socMediaApp.service.PostService;
import com.socMediaApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    PostDTOMapper postDTOMapper;

    @PostMapping("/createPost")
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

    @GetMapping("/getPosts")
    public ResponseEntity<List<PostDTO>> getAllPost() {
        try {
            // Fetch all posts ordered by created date in ascending order
            List<PostDTO> posts = postRepository.findAllByOrderByCreatedDateAsc()
                    .stream()
                    .map(postDTOMapper)
                    .collect(Collectors.toList());

            // Return the list with a 200 OK status
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            // Handle any potential exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
