package com.dongguk.chat.domain.chatroom.dto;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String roomName;
    private List<ParticipantDto> participants;

    public static ChatRoomResponseDto from(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .participants(chatRoom.getParticipants().stream().map(ParticipantDto::from).collect(Collectors.toList()))
                .build();
    }
}
