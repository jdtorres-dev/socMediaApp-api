package com.socMediaApp.socMediaAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postBody;

    private String imageUrl;

    @ManyToOne
    private user userId;
}
