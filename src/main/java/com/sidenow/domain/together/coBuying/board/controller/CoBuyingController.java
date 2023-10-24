package com.sidenow.domain.together.coBuying.board.controller;

import com.sidenow.domain.together.coBuying.board.dto.req.CoBuyingRequest.CoBuyingCreateRequest;
import com.sidenow.domain.together.coBuying.board.dto.req.CoBuyingRequest.CoBuyingUpdateRequest;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.AllCoBuyings;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingCreateResponse;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingGetResponse;
import com.sidenow.domain.together.coBuying.board.dto.res.CoBuyingResponse.CoBuyingUpdateResponse;
import com.sidenow.domain.together.coBuying.board.service.CoBuyingService;
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

import static com.sidenow.domain.together.coBuying.board.constant.CoBuyingConstants.CoBuyingResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/coBuying")
@Tag(name = "공구해요 API", description = "CoBuying")
public class CoBuyingController {

    private final CoBuyingService coBuyingService;

    @PostMapping(value = "/board", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "공구해요 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<CoBuyingCreateResponse>> createCoBuying(@RequestPart(required = false) MultipartFile image,
                                                                                @RequestPart CoBuyingCreateRequest req) {
        log.info("Register CoBuying Controller 진입");
        CoBuyingCreateResponse result = coBuyingService.createCoBuying(req, image);
        log.info("Register CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_CO_BUYING_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "공구해요 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<CoBuyingGetResponse>> getCoBuying(@PathVariable Long id) {
        log.info("Get CoBuying Controller 진입");
        CoBuyingGetResponse result = coBuyingService.getCoBuying(id);
        log.info("Get CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CO_BUYING_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "공구해요 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllCoBuyings>> getAllCoBuying(@PathVariable @Nullable Integer page) {
        log.info("Get All CoBuying Controller 진입");
        AllCoBuyings result = coBuyingService.getCoBuyingList(page);
        log.info("Get All CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_CO_BUYING_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PutMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "공구해요 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<CoBuyingUpdateResponse>> updateCoBuying(@PathVariable Long id, @RequestPart CoBuyingUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update CoBuying Controller 진입");
        CoBuyingUpdateResponse result = coBuyingService.updateCoBuying(id, req, image);
        log.info("Update CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_CO_BUYING_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "공구해요 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteCoBuying(@PathVariable Long id) {
        log.info("Delete CoBuying Controller 진입");
        coBuyingService.deleteCoBuying(id);
        log.info("Delete CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_CO_BUYING_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "공구해요 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeCoBuying(@PathVariable Long id) {
        log.info("Like CoBuying Controller 진입");
        String result = coBuyingService.updateLikeOfCoBuying(id);
        log.info("Like CoBuying Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_CO_BUYING_SUCCESS.getMessage(), result));
    }

}
