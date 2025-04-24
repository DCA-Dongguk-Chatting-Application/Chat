package com.dongguk.chat.domain.chatroom.dto;

import com.dongguk.chat.domain.profile.dto.ProfileResponse;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private UserResponseDto user;
    private ProfileResponse profile;

    public static  ParticipantDto from(User user){
        return ParticipantDto.builder()
                .user(UserResponseDto.fromUser(user))
                .profile(user.getProfile() !=null ? ProfileResponse.from(user.getProfile()) : null)
                .build();
    }
}
