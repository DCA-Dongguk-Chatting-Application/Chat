package com.dongguk.chat.domain.profile;

import com.dongguk.chat.domain.profile.dto.ProfileReqDto;
import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자 프로필 (사용자당 1개)
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserProfile create(ProfileReqDto profileReqDto, User user, String imageUrl){
        UserProfile profile = UserProfile.builder()
                .user(user)
                .nickname(profileReqDto.getNickname())
                .imageUrl(imageUrl)
                .build();
        user.updateUserProfile(profile);
        return profile;
    }

    @Builder
    private UserProfile(String nickname, String imageUrl, User user){
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
