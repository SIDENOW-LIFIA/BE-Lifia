package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest;
import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardCreateRequest;
import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardUpdateRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.*;
import com.sidenow.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FreeBoardService {
    FreeBoardCreateResponse createFreeBoard(FreeBoardCreateRequest req, MultipartFile image);
    AllFreeBoards getFreeBoardList(Integer page);
    FreeBoardGetResponse getFreeBoard(Long id);
    FreeBoardUpdateResponse updateFreeBoard(Long id, FreeBoardUpdateRequest req, MultipartFile image);
    void deleteFreeBoard(Long id);
    String updateLikeOfFreeBoard(Long id);

}
