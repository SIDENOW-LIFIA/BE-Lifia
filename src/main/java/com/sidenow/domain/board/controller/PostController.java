package com.sidenow.domain.board.controller;

import com.sidenow.domain.board.dto.request.PostRequest.PostCreateRequest;
import com.sidenow.domain.board.dto.response.PostResponse;
import com.sidenow.domain.board.dto.response.PostResponse.AllPosts;
import com.sidenow.domain.board.dto.response.PostResponse.PostCreateResponse;
import com.sidenow.domain.board.dto.response.PostResponse.PostDetailResponse;
import com.sidenow.domain.board.service.PostService;
import com.sidenow.global.config.jwt.JwtPrincipal;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
@Tag(name = "게시글 API", description = "Post")
public class PostController {
    private final PostService postService;

    @GetMapping("/postId")
    public ResponseEntity<ResponseDto<PostDetailResponse>> showPost(@PathVariable(name = "postId") Long postId){
        log.info("Show Post API 진입");
        PostDetailResponse result = postService.getPost(postId);
        log.info("Show Post API 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), "게시글을 상세 조회 합니다.", result));
    }


}
