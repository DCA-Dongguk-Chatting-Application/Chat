package com.dongguk.chat.domain.user.dto;

import lombok.*;

@Getter @Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateReq {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}
