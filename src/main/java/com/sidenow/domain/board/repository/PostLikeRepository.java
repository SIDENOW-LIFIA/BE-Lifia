package com.sidenow.domain.board.repository;

import com.sidenow.domain.board.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberIdAndPostId(Long memberId, Long postId);
    void  deleteByMemberIdAndPostId(Long memberId, Long postId);
}
