package com.dongguk.chat.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String userLoginId;
    private String userPassword;
}
