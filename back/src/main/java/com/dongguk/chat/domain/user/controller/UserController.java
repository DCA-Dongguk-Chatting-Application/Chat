package com.dongguk.chat.domain.user.controller;

import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.dongguk.chat.domain.user.dto.UserEditReq;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import com.dongguk.chat.util.UserService;
import com.dongguk.chat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "회원 정보 수정",
            description = """
        JWT accessToken을 통해 인증된 사용자의 정보를 수정합니다.  
        - 요청 헤더에 `Authorization: Bearer {accessToken}` 형식으로 JWT를 전달해야 합니다.  
        - 요청 본문에는 수정할 사용자 정보 (`UserEditReq`)를 JSON 형식으로 전달합니다.  
        - 토큰에서 추출된 사용자 ID 기준으로 수정이 이뤄집니다.
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
            }
    )
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserInfo(@RequestBody UserEditReq dto, HttpServletRequest req){
        String jwtToken = jwtUtil.getJwtToken(req);
        long userId = Long.parseLong(jwtUtil.getClaims(jwtToken).getSubject());
        return ResponseEntity.ok(userService.editUser(userId, dto));
    }
}
