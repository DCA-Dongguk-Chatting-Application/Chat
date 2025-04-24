package com.dongguk.chat.domain.chatroom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomUpgradeDto {
    private Long originalRoomId;
    private List<Long> newUserIds;
    private String roomName;
}
