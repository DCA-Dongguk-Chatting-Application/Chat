package com.dongguk.chat.domain.profile.controller;

import com.dongguk.chat.domain.profile.dto.ProfileReqDto;
import com.dongguk.chat.domain.profile.dto.ProfileResponse;
import com.dongguk.chat.domain.profile.service.ProfileService;
import com.dongguk.chat.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile API", description = "사용자 프로필 생성 및 조회 API")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtUtil jwtUtil;

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(
            summary = "프로필 생성",
            description = """
        사용자의 프로필을 생성합니다.  
        - `userId`에 해당하는 사용자가 존재해야 합니다.  
        - 업로드된 이미지 파일은 서버의 지정된 경로에 저장되며,  
          응답으로는 **서버 도메인을 포함한 전체 imageUrl**이 반환됩니다.  
        - 클라이언트는 이 URL을 그대로 이미지 요청에 사용하면 됩니다.
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 생성 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자 ID가 존재하지 않음", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content)
            }
    )
    public ResponseEntity<ProfileResponse> createProfile(
            @ModelAttribute ProfileReqDto profileReqDto
    ) throws IOException {
        ProfileResponse response = profileService.createUserProfile(profileReqDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "내 프로필 조회",
            description = """
            JWT accessToken을 통해 로그인한 사용자의 프로필 정보를 조회합니다.
            - 업로드된 이미지 파일은 서버의 지정된 경로에 저장되며,  
                응답으로는 **서버 도메인을 포함한 전체 imageUrl**이 반환됩니다.  
            - 클라이언트는 이 URL을 그대로 이미지 요청에 사용하면 됩니다.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
            }
    )
    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(HttpServletRequest req){
        String jwtToken = jwtUtil.getJwtToken(req);
        Long userId = Long.parseLong(jwtUtil.getClaims(jwtToken).getSubject());
        return ResponseEntity.ok(profileService.getUserProfile(userId));
    }
}