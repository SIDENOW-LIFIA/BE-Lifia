package com.sidenow.domain.together.coBuying.board.service;

import com.sidenow.domain.together.coBuying.board.dto.req.CoBuyingRequest.CoBuyingCreateRequest;
import com.sidenow.domain.together.coBuying.board.dto.req.CoBuyingRequest.CoBuyingUpdateRequest;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.AllCoBuyings;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingCreateResponse;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingGetResponse;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CoBuyingService {
    CoBuyingCreateResponse createCoBuying(CoBuyingCreateRequest req, MultipartFile image);
    AllCoBuyings getCoBuyingList(Integer page);
    CoBuyingGetResponse getCoBuying(Long id);
    CoBuyingUpdateResponse updateCoBuying(Long id, CoBuyingUpdateRequest req, MultipartFile image);
    void deleteCoBuying(Long id);
    String updateLikeOfCoBuying(Long id);

}
