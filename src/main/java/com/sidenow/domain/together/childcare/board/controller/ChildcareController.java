package com.sidenow.domain.together.childcare.board.controller;

import com.sidenow.domain.together.childcare.board.service.ChildcareService;
import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareCreateRequest;
import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareUpdateRequest;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.AllChildcares;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareCreateResponse;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareGetResponse;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.ChildcareUpdateResponse;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.sidenow.domain.together.childcare.board.constant.ChildcareConstants.ChildcareResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/childcare")
@Tag(name = "육아해요 API", description = "Childcare")
public class ChildcareController {

    private final ChildcareService childcareService;

    @PostMapping(value = "/board", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "육아해요 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<ChildcareCreateResponse>> createChildcare(@RequestPart(required = false) MultipartFile image,
                                                                                @RequestPart ChildcareCreateRequest req) {
        log.info("Register Childcare Controller 진입");
        ChildcareCreateResponse result = childcareService.createChildcare(req, image);
        log.info("Register Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CHILDCARE_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "육아해요 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<ChildcareGetResponse>> getChildcare(@PathVariable Long id) {
        log.info("Get Childcare Controller 진입");
        ChildcareGetResponse result = childcareService.getChildcare(id);
        log.info("Get Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CHILDCARE_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "육아해요 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllChildcares>> getAllChildcare(@PathVariable @Nullable Integer page) {
        log.info("Get All Childcare Controller 진입");
        AllChildcares result = childcareService.getChildcareList(page);
        log.info("Get All Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CHILDCARE_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PutMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "육아해요 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<ChildcareUpdateResponse>> updateChildcare(@PathVariable Long id, @RequestPart ChildcareUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update Childcare Controller 진입");
        ChildcareUpdateResponse result = childcareService.updateChildcare(id, req, image);
        log.info("Update Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_CHILDCARE_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "육아해요 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteChildcare(@PathVariable Long id) {
        log.info("Delete Childcare Controller 진입");
        childcareService.deleteChildcare(id);
        log.info("Delete Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_CHILDCARE_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "육아해요 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeChildcare(@PathVariable Long id) {
        log.info("Like Childcare Controller 진입");
        String result = childcareService.updateLikeOfChildcare(id);
        log.info("Like Childcare Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CHILDCARE_SUCCESS.getMessage(), result));
    }

}
