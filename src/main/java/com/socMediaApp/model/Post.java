package com.socMediaApp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "sma_post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String body;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = true)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean isDelete = false;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                ", isDelete=" + isDelete +
                '}';
    }
}
