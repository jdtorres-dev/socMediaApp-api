package com.socMediaApp.repository;

import com.socMediaApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    @SuppressWarnings("null")
    Optional<Comment> findById(Long id);

    List<Comment> findByPostIdAndIsDeleteFalseOrderByCreatedDateDesc(Long postId);

}

