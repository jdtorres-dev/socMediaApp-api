package com.socMediaApp.repository;

import com.socMediaApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findAllByOrderByCreatedDateDesc();

    List<Post> findByIsDeleteFalseOrderByCreatedDateDesc();

    Optional<Post> findById(Integer id);

    List<Post> findByUserIdAndIsDeleteFalseOrderByCreatedDateDesc(Long id);

}
