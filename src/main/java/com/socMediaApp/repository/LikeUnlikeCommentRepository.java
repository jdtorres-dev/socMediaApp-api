package com.socMediaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socMediaApp.model.LikeUnlikeComment;

public interface LikeUnlikeCommentRepository extends JpaRepository<LikeUnlikeComment, Long>{

    Optional<LikeUnlikeComment> findFirstByUserIdAndCommentIdOrderByCreatedDateDesc(Long userId, Long commentId);

    @Query("SELECT COUNT(l) FROM LikeUnlikeComment l WHERE l.comment.id = :commentId AND l.isLike = TRUE")
        Long countLikesByCommentId(@Param("commentId") Long commentId);
}
