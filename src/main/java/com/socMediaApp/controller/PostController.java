package com.socMediaApp.controller;

import com.socMediaApp.dto.*;
import com.socMediaApp.model.Comment;
import com.socMediaApp.model.LikeUnlikeComment;
import com.socMediaApp.model.LikeUnlikePost;
import com.socMediaApp.model.Post;
import com.socMediaApp.model.User;
import com.socMediaApp.repository.CommentRepository;
import com.socMediaApp.repository.LikeUnlikeCommentRepository;
import com.socMediaApp.repository.LikeUnlikePostRepo;
import com.socMediaApp.repository.PostRepository;
import com.socMediaApp.service.CommentService;
import com.socMediaApp.service.PostService;
import com.socMediaApp.service.UserService;

import org.springframework.beans.BeanUtils;
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
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeUnlikePostRepo likePostRepo;

    @Autowired
    private LikeUnlikeCommentRepository likeCommentRepo;

    @Autowired
    PostDTOMapper postDTOMapper;

    @Autowired
    CommentDTOMapper coomentDTOMapper;

    @Autowired
    LikeUnlikePostDTOMapper likeUnlikePostDTOMapper;

    @Autowired
    LikeUnlikeCommentDtoMapper likeUnlikeCommentDtoMapper;

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
            List<PostDTO> posts = postRepository.findByIsDeleteFalseOrderByCreatedDateDesc()
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

    @PostMapping("/likePost")
    public ResponseEntity<?> likePost(@RequestBody LikeUnlikePost likePost) {
        Optional <User> currentUser = userService.getCurrentUser(likePost.getUser().getId());
        Optional <Post> currentPost = postService.getCurrentPost(likePost.getPost().getId());

        likePost.setPost(currentPost.get());
        likePost.setUser(currentUser.get());
        likePost.setIsLike(true);
        likePost.setCreatedDate(LocalDateTime.now());
        LikeUnlikePost likePostS = likePostRepo.save(likePost);

        return ResponseEntity.ok("Successfully Like post with ID: " + likePostS.getId());
    }

    @PostMapping("/likeComment")
    public ResponseEntity<?> likeComment(@RequestBody LikeUnlikeComment likeComment ) {
        try {
            Optional<User> user = userService.getCurrentUser(likeComment.getUser().getId());
            Optional<Comment> comment = commentService.getCurrentComment(likeComment.getComment().getId());

            if (user.isEmpty() || comment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No user or post was found.");
            }
    
            likeComment.setUser(user.get());
            likeComment.setComment(comment.get());
            likeComment.setIsLike(true);
            likeComment.setCreatedDate(LocalDateTime.now());
    
            likeCommentRepo.save(likeComment);
    
            return ResponseEntity.ok("Successfully liked comment");
        } catch(Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurresd while processing the request.");
        }
       
    }

    @PutMapping("/unlikePost")
    public ResponseEntity<?> unlikePost(@RequestParam Long id) {
        try {
            Optional<LikeUnlikePost> unLikeOptional = likePostRepo.findById(id);

            if (unLikeOptional.isPresent()) {
                LikeUnlikePost unlikePost = unLikeOptional.get();
                unlikePost.setIsLike(false);
                likePostRepo.save(unlikePost);
                return ResponseEntity.ok("Successfully unlike the post!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/unlikeComment")
    public ResponseEntity<?> unlikeComment(@RequestParam Long id) {
        try {
            Optional<LikeUnlikeComment> unLikeOptional = likeCommentRepo.findById(id);

            if (unLikeOptional.isPresent()) {
                LikeUnlikeComment unlikeComment = unLikeOptional.get();
                unlikeComment.setIsLike(false);
                likeCommentRepo.save(unlikeComment);
                return ResponseEntity.ok("Successfully unlike the comment!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getLikeUnlikePost")
    public ResponseEntity<LikeUnlikePostDTO> getLikeUnlikePost(
            @RequestParam Long userId,
            @RequestParam Long postId) {
        try {
            // Find the Like/Unlike Post using both userId and postId
            LikeUnlikePostDTO likeUnlikePost = likePostRepo.findFirstByUserIdAndPostIdOrderByCreatedDateDesc(userId, postId)
                    .map(likeUnlikePostDTOMapper)
                    .orElse(null);

            if (likeUnlikePost == null) {
                return ResponseEntity.ok(null);
            }

            return ResponseEntity.ok(likeUnlikePost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getLikeUnlikeComment")
    public ResponseEntity<LikeUnlikeCommentDto> getLikeUnlikeComment(
            @RequestParam Long userId,
            @RequestParam Long commentId) {

        try {
            LikeUnlikeCommentDto likeUnlikeComment = likeCommentRepo.findFirstByUserIdAndCommentIdOrderByCreatedDateDesc(userId, commentId)
                    .map(likeUnlikeCommentDtoMapper)
                    .orElse(null);

            if (likeUnlikeComment == null) {
                return ResponseEntity.ok(null);
            }

            return ResponseEntity.ok(likeUnlikeComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getAllLikeComment")
    public ResponseEntity<Long> getAllLikeComment(
            @RequestParam Long commentId) {

        try {
            Long likeCount = likeCommentRepo.countLikesByCommentId(commentId);
                
            if (likeCount == null || likeCount == 0) {
                return ResponseEntity.ok(0L);  
            }

            return ResponseEntity.ok(likeCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getAllLikePost")
    public ResponseEntity<Long> getAllLikePost(
            @RequestParam Long postId) {

        try {
            Long likeCount = likePostRepo.countLikesByPostId(postId);
                
            if (likeCount == null || likeCount == 0) {
                return ResponseEntity.ok(0L);  
            }

            return ResponseEntity.ok(likeCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

     @GetMapping("/getPostsByUser/{id}")
    public ResponseEntity<?> getPostsByUser(@PathVariable Long id) {
        try {
            List<Post> posts = postRepository.findByUserIdAndIsDeleteFalseOrderByCreatedDateDesc(id);

            if (posts.isEmpty()) {
                return ResponseEntity.ok(List.of());
            }

            List<PostDTO> postDTOs = posts.stream()
            .map(postDTOMapper::apply)
            .toList();

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving posts.");
        }
    }

    @PutMapping("/deletePost/{id}")
    public ResponseEntity<?> softDeletePost(@PathVariable Long id, @RequestBody Post post) {

        Optional<Post> result = postRepository.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Post does not exist.");
        }

        try {
            Post existing = result.get();
        
            BeanUtils.copyProperties(post, existing, "id", "userId", "body", "imageUrl", "createdDate"); 

            existing.setIsDelete(true);
            
            postService.updatePost(existing);

            return ResponseEntity.ok("Post successfully deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred while processing the request.");
        }
    }

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<?> updatePostById(@PathVariable Long id, @RequestBody Post post) {

        Optional<Post> result = postRepository.findById(id); 

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Post does not exist.");
        }

        try {
            Post existing = result.get();
        
            BeanUtils.copyProperties(post, existing, "id", "userId", "isDelete", "createdDate"); 

            existing.setBody(post.getBody());
            existing.setImageUrl(post.getImageUrl());
            // existing.setCreatedDate(LocalDateTime.now());
            // existing.setCreatedDate(post.getCreatedDate());
            
            postService.updatePost(existing);

            return ResponseEntity.ok("User update successful");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred while processing the request.");
        }
    }
}
