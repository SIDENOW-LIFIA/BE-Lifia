package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardCreatePostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostListResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FreeBoardService {
    FreeBoardCheck createFreeBoardPost(String accessToken, List<MultipartFile> multipartFile, FreeBoardCreatePostRequest createFreeBoardPostRequest);
    List<FreeBoardGetPostListResponse> getFreeBoardPostList(String accessToken, int pageNumber);
    FreeBoardGetPostResponse getFreeBoardPost(Long freeBoardPostId);
    FreeBoardCheck updateFreeBoardPost(String accessToken, List<MultipartFile> multipartFile, )
}
