package com.sidenow.domain.boardType.childcare.application.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/childcare")
@Tag(name = "공동육아 신청폼 API", description = "Application")
public class ApplicationController {
}
