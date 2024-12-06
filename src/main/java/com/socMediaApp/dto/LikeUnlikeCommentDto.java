package com.socMediaApp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LikeUnlikeCommentDto {

    private Long id;
    private Boolean isLike;
    private CommentUserDto user;
    private CommentDTO comment;
    private LocalDateTime createdDate;


}
