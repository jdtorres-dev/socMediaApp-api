package com.socMediaApp.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CommentDTO {
    private Long id;
    private String commentBody;
    private LocalDateTime createdDate;
    private PostUserDTO user;
    private PostDTO post;

    public CommentDTO( Long id, String commentBody, LocalDateTime createdDate, PostUserDTO user, PostDTO post) {
        this.id = id;
        this.commentBody = commentBody;
        this.createdDate = createdDate;
        this.user = user;
        this.post = post;
    }
}
