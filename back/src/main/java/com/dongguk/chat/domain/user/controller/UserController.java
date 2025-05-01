package com.dongguk.chat.domain.user.controller;

import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import com.dongguk.chat.util.UserService;
import com.dongguk.chat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody UserCreateReq userCreateReq){
       return ResponseEntity.ok(userService.userCreate(userCreateReq));
    }

    @GetMapping
    public UserResponseDto getUserInfo(HttpServletRequest req){
        Claims claims = jwtUtil.getClaims(jwtUtil.getJwtToken(req));
        String userId = claims.getSubject();
        return UserResponseDto.fromUser(userService.getUserInfo((long) Integer.parseInt(userId)));
    }
}
