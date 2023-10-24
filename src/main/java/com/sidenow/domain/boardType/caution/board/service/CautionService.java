package com.sidenow.domain.boardType.caution.board.service;

import com.sidenow.domain.boardType.caution.board.dto.req.CautionRequest.CautionCreateRequest;
import com.sidenow.domain.boardType.caution.board.dto.req.CautionRequest.CautionUpdateRequest;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.AllCautions;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionCreateResponse;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionGetResponse;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CautionService {
    CautionCreateResponse createCaution(CautionCreateRequest req, MultipartFile image);
    AllCautions getCautionList(Integer page);
    CautionGetResponse getCaution(Long id);
    CautionUpdateResponse updateCaution(Long id, CautionUpdateRequest req, MultipartFile image);
    void deleteCaution(Long id);
    String updateLikeOfCaution(Long id);

}
