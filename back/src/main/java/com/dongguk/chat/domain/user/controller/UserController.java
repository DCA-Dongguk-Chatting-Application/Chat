package com.dongguk.chat.domain.user.controller;

import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import com.dongguk.chat.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody UserCreateReq userCreateReq){
       return ResponseEntity.ok(userService.userCreate(userCreateReq));
    }
}
