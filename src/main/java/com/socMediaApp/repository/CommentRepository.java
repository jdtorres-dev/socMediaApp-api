package com.socMediaApp.repository;

import com.socMediaApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByPostIdAndIsDeleteFalseOrderByCreatedDateDesc(Long postId);

}

