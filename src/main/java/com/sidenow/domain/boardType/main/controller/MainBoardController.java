package com.sidenow.domain.boardType.main.controller;

import com.sidenow.domain.boardType.main.service.MainBoardService;
import io.lettuce.core.GeoArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mainBoard")
public class MainBoardController {

    private final MainBoardService mainBoardService;
}
