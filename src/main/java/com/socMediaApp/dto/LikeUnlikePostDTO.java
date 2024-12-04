package com.socMediaApp.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class LikeUnlikePostDTO {
    private Long id;
    private Boolean isLike;
    private PostUserDTO user;
    private PostDTO post;
    private LocalDateTime createdDate;

    public LikeUnlikePostDTO(Long id, Boolean isLike, PostUserDTO user, PostDTO post, LocalDateTime createdDate) {
        this.id = id;
        this.isLike = isLike;
        this.user = user;
        this.post = post;
        this.createdDate = createdDate;
    }


}
