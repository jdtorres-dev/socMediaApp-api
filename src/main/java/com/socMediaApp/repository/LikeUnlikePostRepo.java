package com.socMediaApp.repository;

import com.socMediaApp.model.LikeUnlikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeUnlikePostRepo extends JpaRepository<LikeUnlikePost, Long>{
    Optional<LikeUnlikePost> findFirstByUserIdAndPostIdOrderByCreatedDateDesc(Long userId, Long postId);

    //Page<LikeUnlikePost> findByUserIdAndPostIdOrderByCreatedDateDesc(Long userId, Long postId, Pageable pageable);
}
