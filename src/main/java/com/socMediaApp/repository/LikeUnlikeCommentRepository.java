package com.socMediaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socMediaApp.model.LikeUnlikeComment;

public interface LikeUnlikeCommentRepository extends JpaRepository<LikeUnlikeComment, Long>{

    Optional<LikeUnlikeComment> findFirstByUserIdAndCommentIdOrderByCreatedDateDesc(Long userId, Long commentId);
}
