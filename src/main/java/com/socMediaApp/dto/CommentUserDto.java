package com.socMediaApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentUserDto {

    private Long id;
    private String name;
    private String username;
    private String imageUrl;
    private String email;
}
