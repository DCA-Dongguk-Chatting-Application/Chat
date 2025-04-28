package com.dongguk.chat.security.controller;

import com.dongguk.chat.security.dto.JwtToken;
import com.dongguk.chat.security.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class LoginController {
    @Operation(summary = "사용자 로그인")
    @PostMapping("/login")
    public JwtToken userLogin(@RequestBody UserLoginDto userLoginDto){
        return JwtToken.builder().accessToken(userLoginDto.getUserLoginId()).build();
    }
}
