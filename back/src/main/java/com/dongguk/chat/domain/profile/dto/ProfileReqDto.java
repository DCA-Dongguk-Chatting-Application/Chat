package com.dongguk.chat.domain.profile.dto;

import com.dongguk.chat.domain.user.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReqDto {
    private String nickname;
    private MultipartFile image;
    private Long userId;
}
