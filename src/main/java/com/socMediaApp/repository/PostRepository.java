package com.socMediaApp.repository;

import com.socMediaApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
}
