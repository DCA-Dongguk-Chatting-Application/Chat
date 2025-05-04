package com.dongguk.chat.domain.profile.dto;

import com.dongguk.chat.domain.profile.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    @Schema(description = "프로필 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 닉네임", example = "잠곰이")
    private String nickname;

    @Schema(description = "프로필 이미지 URL, 사용할 때는 서버 주소를 붙이고 사용해야합니다. ex) localhost/static/profile/abc1.png", example = "/static/profile/abcd1234.png")
    private String imageUrl;

    @Schema(description = "유저 고유 ID", example = "1")
    private Long userId;

    public static ProfileResponse from(UserProfile userProfile){
        return ProfileResponse.builder()
                .id(userProfile.getId())
                .nickname(userProfile.getNickname())
                .imageUrl(userProfile.getImageUrl())
                .userId(userProfile.getUser().getId())
                .build();
    }
}
