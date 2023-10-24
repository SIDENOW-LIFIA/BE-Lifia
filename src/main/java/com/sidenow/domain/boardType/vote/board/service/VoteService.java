package com.sidenow.domain.boardType.vote.board.service;

import com.sidenow.domain.boardType.vote.board.dto.req.VoteRequest.VoteCreateRequest;
import com.sidenow.domain.boardType.vote.board.dto.req.VoteRequest.VoteUpdateRequest;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.AllVotes;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteCreateResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteGetResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteResponse.VoteUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VoteService {
    VoteCreateResponse createVote(VoteCreateRequest req, MultipartFile image);
    AllVotes getVoteList(Integer page);
    VoteGetResponse getVote(Long id);
    VoteUpdateResponse updateVote(Long id, VoteUpdateRequest req, MultipartFile image);
    void deleteVote(Long id);
    String updateLikeOfVote(Long id);

}
