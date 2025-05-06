package com.dongguk.chat.domain.profile.dto;

import com.dongguk.chat.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReqDto {

    @Schema(description = "사용자 닉네임", example = "잠곰이")
    private String nickname;

    @Schema(description = "프로필 이미지 파일 (multipart/form-data 형식)")
    private MultipartFile image;

    @Schema(description = "유저 고유 ID", example = "1")
    private Long userId;
}
