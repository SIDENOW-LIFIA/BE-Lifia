package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardRegisterPostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.AllFreeBoards;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostListResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FreeBoardService {
    FreeBoardCheck registerFreeBoardPost(List<MultipartFile> multipartFile, FreeBoardRegisterPostRequest createFreeBoardPostRequest);
    AllFreeBoards getFreeBoardPostList(Integer page);
    FreeBoardGetPostResponse getFreeBoardPost(Long freeBoardPostId);
    FreeBoardCheck updateFreeBoardPost(String accessToken, List<MultipartFile> multipartFile, FreeBoardRegisterPostRequest freeBoardCreatePostRequest);
    FreeBoardCheck deleteFreeBoardPost(Long freeBoardPostId);
}
