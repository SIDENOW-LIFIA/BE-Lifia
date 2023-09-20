package com.sidenow.domain.boardType.childCare.board.service;

import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest;
import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest.ChildCareBoardRegisterPostRequest;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.AllChildCareBoards;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardCheck;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardGetPostListResponse;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardGetPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChildCareBoardService {
	ChildCareBoardCheck registerChildCareBoardPost(List<MultipartFile> multipartFile, ChildCareBoardRegisterPostRequest createChildCareBoardPostRequest);
    AllChildCareBoards getChildCareBoardPostList(Integer page);
    ChildCareBoardGetPostResponse getChildCareBoardPost(Long childCareBoardPostId);
    ChildCareBoardCheck updateChildCareBoardPost(List<MultipartFile> multipartFile, Long childCareBoardPostId, ChildCareBoardRequest.ChildCareBoardUpdatePostRequest childCareBoardUpdatePostRequest);
    ChildCareBoardCheck deleteChildCareBoardPost(Long childCareBoardPostId);
}
