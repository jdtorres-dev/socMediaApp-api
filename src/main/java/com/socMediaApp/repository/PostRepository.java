package com.socMediaApp.repository;

import com.socMediaApp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findAllByOrderByCreatedDateDesc();

    Optional<Post> findById(Integer id);
}
