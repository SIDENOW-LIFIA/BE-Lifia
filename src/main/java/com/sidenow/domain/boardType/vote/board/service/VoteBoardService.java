package com.sidenow.domain.boardType.vote.board.service;

import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest;
import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest.VoteBoardRegisterPostRequest;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.AllVoteBoards;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardCheck;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardGetPostListResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardGetPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VoteBoardService {
    VoteBoardCheck registerVoteBoardPost(List<MultipartFile> multipartFile, VoteBoardRegisterPostRequest createVoteBoardPostRequest);
    AllVoteBoards getVoteBoardPostList(Integer page);
    VoteBoardGetPostResponse getVoteBoardPost(Long voteBoardPostId);
    VoteBoardCheck updateVoteBoardPost(List<MultipartFile> multipartFile, Long voteBoardPostId, VoteBoardRequest.VoteBoardUpdatePostRequest voteBoardUpdatePostRequest);
    VoteBoardCheck deleteVoteBoardPost(Long voteBoardPostId);
}
