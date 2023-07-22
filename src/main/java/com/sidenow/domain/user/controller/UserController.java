package com.sidenow.domain.user.controller;

import com.sidenow.domain.user.entity.User;
import com.sidenow.domain.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Getter
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
}
