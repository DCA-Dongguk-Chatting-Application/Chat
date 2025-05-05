package com.dongguk.chat.domain.user.dto;


import com.dongguk.chat.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;

    private String username; // 로그인 IDz

    private String password; // 암호화된 비밀번호

    private  String phoneNumber;

    private String email;


    public static UserResponseDto fromUser(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }
}
