package com.socMediaApp.controller;

import com.socMediaApp.dto.CommentDTO;
import com.socMediaApp.dto.CommentDTOMapper;
import com.socMediaApp.dto.PostDTO;
import com.socMediaApp.dto.PostDTOMapper;
import com.socMediaApp.model.Comment;
import com.socMediaApp.model.Post;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.CommentRepository;
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
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    PostDTOMapper postDTOMapper;

    @Autowired
    CommentDTOMapper coomentDTOMapper;

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
            List<PostDTO> posts = postRepository.findAllByOrderByCreatedDateDesc()
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

    @GetMapping("/getPost/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        try {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

            // Map the post entity to a PostDTO
            PostDTO postDTO = postDTOMapper.apply(post);

            // Return the postDTO with a 200 OK status
            return ResponseEntity.ok(postDTO);
        } catch (ResourceNotFoundException e) {
            // Return 404 if the post was not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Return 500 for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        Optional <User> currentUser = userService.getCurrentUser(comment.getUser().getId());
        Optional <Post> currentPost = postService.getCurrentPost(comment.getPost().getId());

        comment.setPost(currentPost.get());
        comment.setUser(currentUser.get());
        comment.setCreatedDate(LocalDateTime.now());
        Comment createdComment = commentRepository.save(comment);

        return ResponseEntity.ok("Comment created successfully with ID: " + createdComment.getId());
    }

    @GetMapping("/getCommentsByPostId/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        try {

            List<CommentDTO> comments = commentRepository.findByPostIdAndIsDeleteFalseOrderByCreatedDateDesc(postId)
                    .stream()
                    .map(coomentDTOMapper)
                    .collect(Collectors.toList());

            // Return the list with a 200 OK status
            return ResponseEntity.ok(comments);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getCommentById")
    public ResponseEntity<CommentDTO> getCommentById(@RequestParam Long id) {
        try {
            CommentDTO comment = commentRepository.findById(id)
                    .map(coomentDTOMapper)
                    .orElse(null);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/deleteComment")
    public ResponseEntity<?> softDeleteComment(@RequestParam Long commentId) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(commentId);

            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.setIsDelete(true);
                commentRepository.save(comment);
                return ResponseEntity.ok("Comment successfully deleted!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateComment")
    public ResponseEntity<?> updateComment(@RequestParam Long commentId, @RequestParam String commentBody) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(commentId);

            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.setCommentBody(commentBody);
                commentRepository.save(comment);
                return ResponseEntity.ok("Comment successfully updated!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
