package com.dongguk.chat.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditReq {
    private String username;
    private String email;
    private String phoneNumber;
}
