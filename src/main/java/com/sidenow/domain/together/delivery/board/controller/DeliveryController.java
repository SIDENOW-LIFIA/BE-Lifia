package com.sidenow.domain.together.delivery.board.controller;

import com.sidenow.domain.together.delivery.board.dto.req.DeliveryRequest.DeliveryCreateRequest;
import com.sidenow.domain.together.delivery.board.dto.req.DeliveryRequest.DeliveryUpdateRequest;
import com.sidenow.domain.together.delivery.board.dto.res.DeliveryResponse.AllDeliverys;
import com.sidenow.domain.together.delivery.board.dto.res.DeliveryResponse.DeliveryCreateResponse;
import com.sidenow.domain.together.delivery.board.dto.res.DeliveryResponse.DeliveryGetResponse;
import com.sidenow.domain.together.delivery.board.dto.res.DeliveryResponse.DeliveryUpdateResponse;
import com.sidenow.domain.together.delivery.board.service.DeliveryService;
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

import static com.sidenow.domain.together.delivery.board.constant.DeliveryConstants.DeliveryResponseMessage.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/delivery")
@Tag(name = "배달해요 API", description = "Delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping(value = "/board", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "배달해요 게시글 등록", description = "게시글을 등록합니다.")
    public ResponseEntity<ResponseDto<DeliveryCreateResponse>> createDelivery(@RequestPart(required = false) MultipartFile image,
                                                                                @RequestPart DeliveryCreateRequest req) {
        log.info("Register Delivery Controller 진입");
        DeliveryCreateResponse result = deliveryService.createDelivery(req, image);
        log.info("Register Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_DELIVERY_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "배달해요 게시글 단건 조회", description = "게시글을 단건 조회합니다.")
    public ResponseEntity<ResponseDto<DeliveryGetResponse>> getDelivery(@PathVariable Long id) {
        log.info("Get Delivery Controller 진입");
        DeliveryGetResponse result = deliveryService.getDelivery(id);
        log.info("Get Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_DELIVERY_POST_SUCCESS.getMessage(), result));
    }

    @GetMapping("/{page}")
    @Operation(summary = "배달해요 게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ResponseDto<AllDeliverys>> getAllDelivery(@PathVariable @Nullable Integer page) {
        log.info("Get All Delivery Controller 진입");
        AllDeliverys result = deliveryService.getDeliveryList(page);
        log.info("Get All Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_DELIVERY_POST_LIST_SUCCESS.getMessage(), result));
    }

    @PutMapping(value = "/board/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "배달해요 게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ResponseDto<DeliveryUpdateResponse>> updateDelivery(@PathVariable Long id, @RequestPart DeliveryUpdateRequest req, @RequestPart(required = false) MultipartFile image) {
        log.info("Update Delivery Controller 진입");
        DeliveryUpdateResponse result = deliveryService.updateDelivery(id, req, image);
        log.info("Update Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.update(HttpStatus.OK.value(), UPDATE_DELIVERY_POST_SUCCESS.getMessage(), result));
    }

    @DeleteMapping(value = "/board/{id}")
    @Operation(summary = "배달해요 게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteDelivery(@PathVariable Long id) {
        log.info("Delete Delivery Controller 진입");
        deliveryService.deleteDelivery(id);
        log.info("Delete Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.delete(HttpStatus.OK.value(), DELETE_DELIVERY_POST_SUCCESS.getMessage()));
    }

    @PostMapping("/board/{id}")
    @Operation(summary = "배달해요 게시글 좋아요", description = "사용자가 게시글 좋아요를 누릅니다.")
    public ResponseEntity<ResponseDto<String>> likeDelivery(@PathVariable Long id) {
        log.info("Like Delivery Controller 진입");
        String result = deliveryService.updateLikeOfDelivery(id);
        log.info("Like Delivery Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LIKE_DELIVERY_SUCCESS.getMessage(), result));
    }

}
