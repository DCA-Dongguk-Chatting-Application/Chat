package com.dongguk.chat.domain.profile.controller;

import com.dongguk.chat.domain.profile.dto.ProfileReqDto;
import com.dongguk.chat.domain.profile.dto.ProfileResponse;
import com.dongguk.chat.domain.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile API", description = "사용자 프로필 생성 및 조회 API")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(
            summary = "프로필 생성",
            description = "사용자의 프로필을 생성합니다. userId에 해당하는 사용자가 존재해야 합니다.",
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
}