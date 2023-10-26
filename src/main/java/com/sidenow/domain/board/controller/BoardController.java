package com.sidenow.domain.board.controller;

import com.sidenow.domain.board.dto.request.PostRequest.PostCreateRequest;
import com.sidenow.domain.board.dto.response.PostResponse.AllPosts;
import com.sidenow.domain.board.dto.response.PostResponse.PostCreateResponse;
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
@RequestMapping("/boards")
@Tag(name = "게시판 API", description = "Board")
public class BoardController {
    private final PostService postService;

    @PostMapping("/{boardId}/posts")
    @Operation(summary = "게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<PostCreateResponse>> createPost(@PathVariable(name = "boardId") Long boardId,
                                                                      @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
                                                                      @RequestPart(required = false)MultipartFile image,
                                                                      @RequestPart PostCreateRequest request){
        log.info("Create Post API 진입");
        PostCreateResponse result = postService.createPost(boardId, jwtPrincipal.memberId, image, request);
        log.info("Create Post API 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), "게시글이 생성되었습니다.", result));
    }


    @GetMapping("/{boardId}/posts/{page}")
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllPosts>> showPostsByBoard(@PathVariable(name = "boardId") Long boardId,
                                                                  @PathVariable(name = "page") @Nullable Integer page,
                                                                  @AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        log.info("Show Posts By Board API 진입");
        AllPosts result = postService.getPostsByBoard(boardId, page, jwtPrincipal.memberId);
        log.info("Show Posts By Board API 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), "게시글을 전체 조회하였습니다.", result));
    }
}
