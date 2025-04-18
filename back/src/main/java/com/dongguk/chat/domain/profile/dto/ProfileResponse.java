package com.dongguk.chat.domain.profile.dto;

import com.dongguk.chat.domain.profile.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private Long id;
    private String nickname;
    private String imageUrl;
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
