package com.sidenow.domain.freeboard.board.service;

import com.sidenow.domain.freeboard.board.dto.req.FreeBoardRequest.FreeBoardCreateRequest;
import com.sidenow.domain.freeboard.board.dto.req.FreeBoardRequest.FreeBoardUpdateRequest;
import com.sidenow.domain.freeboard.board.dto.res.FreeBoardResponse.*;
import org.springframework.web.multipart.MultipartFile;

public interface FreeBoardService {
    FreeBoardCreateResponse createFreeBoard(FreeBoardCreateRequest req, MultipartFile image);
    AllFreeBoards getFreeBoardList(Integer page);
    FreeBoardGetResponse getFreeBoard(Long id);
    FreeBoardUpdateResponse updateFreeBoard(Long id, FreeBoardUpdateRequest req, MultipartFile image);
    void deleteFreeBoard(Long id);
    String updateLikeOfFreeBoard(Long id);

}
