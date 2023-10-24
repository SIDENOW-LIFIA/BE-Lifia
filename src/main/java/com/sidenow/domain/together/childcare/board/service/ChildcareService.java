package com.sidenow.domain.together.childcare.board.service;

import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareCreateRequest;
import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareUpdateRequest;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.AllChildcares;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareCreateResponse;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareGetResponse;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareUpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ChildcareService {
    ChildcareCreateResponse createChildcare(ChildcareCreateRequest req, MultipartFile image);
    AllChildcares getChildcareList(Integer page);
    ChildcareGetResponse getChildcare(Long id);
    ChildcareUpdateResponse updateChildcare(Long id, ChildcareUpdateRequest req, MultipartFile image);
    void deleteChildcare(Long id);
    String updateLikeOfChildcare(Long id);

}
