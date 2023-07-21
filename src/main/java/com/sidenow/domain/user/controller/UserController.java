package com.sidenow.domain.user.controller;

import com.sidenow.domain.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Getter
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
}
