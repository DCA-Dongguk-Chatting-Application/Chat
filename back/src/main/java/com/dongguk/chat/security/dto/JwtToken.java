package com.dongguk.chat.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String accessToken;
}
