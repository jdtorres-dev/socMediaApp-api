package com.socMediaApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.socMediaApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PostDTO {
    private Long id;
    private String body;
    private String imageUrl;
    private LocalDateTime createdDate;
    private PostUserDTO user;

    public PostDTO(Long id, LocalDateTime createdDate, String body, String imageUrl, PostUserDTO apply) {
        this.id = id;
        this.body = body;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.user = apply;
    }
}
