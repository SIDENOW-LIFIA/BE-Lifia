package com.sidenow.domain.board.service;

import com.sidenow.domain.board.dto.request.PostRequest.PostCreateRequest;
import com.sidenow.domain.board.dto.response.PostResponse.PostCreateResponse;
import com.sidenow.domain.board.dto.response.PostResponse.PostSimpleResponse;
import com.sidenow.domain.board.entity.Board;
import com.sidenow.domain.board.entity.Post;
import com.sidenow.domain.board.repository.BoardRepository;
import com.sidenow.domain.board.repository.PostLikeRepository;
import com.sidenow.domain.board.repository.PostRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.aws.service.AwsS3Service;
import com.sidenow.global.error.exception.NotFoundException;
import com.sidenow.global.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.sidenow.domain.board.dto.response.PostResponse.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostServiceImpl implements PostService{
    private static final int BOARD_PAGE_SIZE = 10;
    private static final Integer HOT_COUNT = 10;
    private final Integer BEST_COUNT = 100;
    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostLikeRepository postLikeRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    @Override
    public PostCreateResponse createPost(Long boardId, Long memberId,
                                                      MultipartFile image, PostCreateRequest request){
        log.info("Create Post Service 진입");
        Board board = findBoard(boardId);
        Member member = findMember(memberId);
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        String imageUrl = "";
        if (image != null){
            imageUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imageUrl);
        }
        Post post = PostCreateRequest.to(board, member, imageUrl, request);
        postRepository.save(post);
        log.info("Create Post Service 종료");

        return PostCreateResponse.from(post);
    }

    // 게시판별 게시글 목록 조회
    @Transactional
    @Override
    public AllPosts getPostsByBoard(Long boardId, Integer page, Long memberId){
        log.info("Get Posts By Board Service 진입");
        findBoard(boardId);
        findMember(memberId);
        if (page == null){
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, BOARD_PAGE_SIZE);
        Page<Post> postPage = postRepository.findByOrderByCreatedAtDesc(pageable);
        List<PostSimpleResponse> postList = new ArrayList<>();
        for (int i = 0; i < postPage.getContent().size(); i++) {
            postList.add(PostSimpleResponse.from(postPage.getContent().get(i)));
        }
        log.info("Get Posts By Board Service 종료");

        return AllPosts.builder()
                .posts(postList)
                .build();
    }

    // 게시글 상세 조회
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId){
        log.info("Get Post Service 진입");
        Post post = findPost(postId);
        post.increaseHits();
        PostDetailResponse result = PostDetailResponse.from(post);
        log.info("Get Post Service 종료");

        return result;
    }

    // Hot 게시글 조회
    @Override
    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> getHotPosts(Pageable pageable){
        log.info("Get Hot Posts Service 진입");
        Page<Post> posts = postRepository.findPostByLikeCount(HOT_COUNT, pageable);
        log.info("Get Hot Posts Service 종료");

        return posts.map(PostSimpleResponse::from);
    }





    private Board findBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }

    private Post findPost(Long postId){
        return postRepository.findPostById(postId).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
    }

    private Member findMember(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
