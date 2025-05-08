package com.dongguk.chat.domain.chatroom.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomAddparticipantsDto {
    private List<Long> userIds;
}
