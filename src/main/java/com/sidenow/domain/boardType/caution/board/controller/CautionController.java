package com.sidenow.domain.boardType.caution.board.controller;

import com.sidenow.domain.boardType.caution.board.service.CautionService;
import com.sidenow.domain.boardType.caution.board.dto.req.CautionRequest.CautionCreateRequest;
import com.sidenow.domain.boardType.caution.board.dto.req.CautionRequest.CautionUpdateRequest;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.AllCautions;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionCreateResponse;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionGetResponse;
import com.sidenow.domain.boardType.caution.board.dto.res.CautionResponse.CautionUpdateResponse;
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

import static com.sidenow.domain.boardType.caution.board.constant.CautionConstants.CautionResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/caution")
@Tag(name = "조심해요 API", description = "Caution")
public class CautionController {

    private final CautionService cautionService;

    @PostMapping(value = "/board", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "조심해요 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<CautionCreateResponse>> createCaution(@RequestPart(required = false) MultipartFile image,
                                                                                @RequestPart CautionCreateRequest req) {
        log.info("Register Caution Controller 진입");
        CautionCreateResponse result = cautionService.createCaution(req, image);
        log.info("Register Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CAUTION_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "조심해요 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<CautionGetResponse>> getCaution(@PathVariable Long id) {
        log.info("Get Caution Controller 진입");
        CautionGetResponse result = cautionService.getCaution(id);
        log.info("Get Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CAUTION_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "조심해요 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllCautions>> getAllCaution(@PathVariable @Nullable Integer page) {
        log.info("Get All Caution Controller 진입");
        AllCautions result = cautionService.getCautionList(page);
        log.info("Get All Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CAUTION_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PutMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "조심해요 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<CautionUpdateResponse>> updateCaution(@PathVariable Long id, @RequestPart CautionUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update Caution Controller 진입");
        CautionUpdateResponse result = cautionService.updateCaution(id, req, image);
        log.info("Update Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_CAUTION_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "조심해요 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteCaution(@PathVariable Long id) {
        log.info("Delete Caution Controller 진입");
        cautionService.deleteCaution(id);
        log.info("Delete Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_CAUTION_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "조심해요 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeCaution(@PathVariable Long id) {
        log.info("Like Caution Controller 진입");
        String result = cautionService.updateLikeOfCaution(id);
        log.info("Like Caution Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CAUTION_SUCCESS.getMessage(), result));
    }

}
