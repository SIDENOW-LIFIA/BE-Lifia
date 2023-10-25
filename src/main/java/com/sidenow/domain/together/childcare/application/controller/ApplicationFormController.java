package com.sidenow.domain.together.childcare.application.controller;

import com.sidenow.domain.together.childcare.application.constant.ApplicationFormConstants;
import com.sidenow.domain.together.childcare.application.dto.req.ApplicationFormRequest;
import com.sidenow.domain.together.childcare.application.dto.req.ApplicationFormRequest.ApplicationFormCreateRequest;
import com.sidenow.domain.together.childcare.application.dto.res.ApplicationFormResponse;
import com.sidenow.domain.together.childcare.application.dto.res.ApplicationFormResponse.ApplicationFormCreateResponse;
import com.sidenow.domain.together.childcare.application.service.ApplicationFormService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sidenow.domain.together.childcare.application.constant.ApplicationFormConstants.ApplicationFormResponseMessage.CREATE_APPLICATION_FORM_SUCCESS;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/childcare")
@Tag(name = "공동육아 신청폼 API", description = "Application")
public class ApplicationFormController {

    private final ApplicationFormService applicationFormService;
    @PostMapping(value = "/{postId}/application-forms")
    public ResponseEntity<ResponseDto<ApplicationFormCreateResponse>> createApplicationForm(@PathVariable("postId") Long childcareId, @Valid @RequestBody ApplicationFormCreateRequest req){
        log.info("Create Application Form Controller 진입");
        ApplicationFormCreateResponse result = applicationFormService.createApplicationForm(childcareId, req);
        log.info("Create Application Form Controller 종료");

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.CREATED.value(), CREATE_APPLICATION_FORM_SUCCESS.getMessage(), result));
    }

}
