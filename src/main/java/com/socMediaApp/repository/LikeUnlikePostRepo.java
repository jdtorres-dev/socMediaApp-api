package com.socMediaApp.repository;

import com.socMediaApp.model.LikeUnlikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeUnlikePostRepo extends JpaRepository<LikeUnlikePost, Long>{
    Optional<LikeUnlikePost> findFirstByUserIdAndPostIdOrderByCreatedDateDesc(Long userId, Long postId);

    @Query("SELECT COUNT(l) FROM LikeUnlikePost l WHERE l.post.id = :postId AND l.isLike = TRUE")
        Long countLikesByPostId(@Param("postId") Long postId);
}
