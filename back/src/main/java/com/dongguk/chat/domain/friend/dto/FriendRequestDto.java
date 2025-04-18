package com.dongguk.chat.domain.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendRequestDto {
    private Long requesterId;
    private Long receiverId;
}
