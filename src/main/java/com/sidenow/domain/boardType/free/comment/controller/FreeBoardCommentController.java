package com.sidenow.domain.boardType.free.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/board/free/{postId}/comment")
@Tag(name = "FreeBoard Comment API", description = "자유게시판 게시글의 댓글 API 입니다.")
public class FreeBoardCommentController {

}
