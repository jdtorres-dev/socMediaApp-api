package com.socMediaApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckUserErrorResponseDTO {
    
    private String message;
    private String field;
}
