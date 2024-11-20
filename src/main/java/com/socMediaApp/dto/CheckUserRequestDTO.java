package com.socMediaApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class CheckUserRequestDTO {

    private String username;
    private String email;
}
