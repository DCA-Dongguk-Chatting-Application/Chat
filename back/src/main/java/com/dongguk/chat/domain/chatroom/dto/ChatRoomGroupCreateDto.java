package com.dongguk.chat.domain.chatroom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomGroupCreateDto {
    private List<Long> userIds;
    private String roomName;
}
