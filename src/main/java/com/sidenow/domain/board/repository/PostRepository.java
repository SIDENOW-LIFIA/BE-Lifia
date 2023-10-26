package com.sidenow.domain.board.repository;

import com.sidenow.domain.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.board join fetch p.member left join where p.id = :postId")
    Optional<Post> findPostById(Long postId);
    @Query(
            value = "select p from Post p join fetch p.board b join fetch p.member u where p.board.id = :boardId",
            countQuery = "select count(p) from Post p where p.board.id = :boardId"
    )
    Page<Post> findPostsByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);
    @Query(
            value = "select p from Post p join fetch p.board join fetch p.member where p.member.id = :memberId",
            countQuery = "select count(p) from Post p where p.member.id = :memberId"
    )
    Page<Post> findPostsByMemberId(Long memberId, Pageable pageable);

    @Query(
            value = "select p from Post p join fetch p.board join fetch p.member where p.id IN (:postIds)",
            countQuery = "select count(p) from Post p where p.id IN (:postIds)"
    )
    Page<Post> findPostsThatMemberCommentedAt(@Param("postIds") List<Long> postIds, Pageable pageable);

    @Query(value = "select p from Post p join fetch p.board join fetch p.member where upper(function('replace', p.title, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%') or upper(function('replace', p.content, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%')",
            countQuery = "select count(p) from Post p where upper(function('replace', p.title, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%') or upper(function('replace', p.content, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%')")
    Page<Post> searchPosts(String keyword, Pageable pageable);

    @Query(value = "select p from Post p join fetch p.board join fetch p.member where p.board.id = :boardId and (upper(function('replace', p.title, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%') or upper(function('replace', p.content, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%'))",
            countQuery = "select count(p) from Post p where p.board.id = :boardId and (upper(function('replace', p.title, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%') or upper(function('replace', p.content, ' ', '')) like concat('%', upper(function('replace', :keyword, ' ', '')), '%'))")
    Page<Post> searchPostsAtBoard(String keyword, Long boardId, Pageable pageable);

    @Query(
            value = "select p from Post p join fetch p.board join fetch p.member where p.likeCount >= :likeCount",
            countQuery = "select count(p) from Post p where p.likeCount >= :likeCount"
    )
    Page<Post> findPostsByLikeCount(@Param("likeCount") Integer likeCount, Pageable pageable);
}
