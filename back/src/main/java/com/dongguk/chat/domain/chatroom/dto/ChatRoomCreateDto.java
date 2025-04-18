package com.dongguk.chat.domain.chatroom.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateDto {
    private Long myId;
    private Long partnerId;
    private String roomName;
}
