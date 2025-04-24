package com.dongguk.chat.domain.profile.dto;

import com.dongguk.chat.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReqDto {
    private String nickname;
    private MultipartFile image;
    private Long userId;
}
