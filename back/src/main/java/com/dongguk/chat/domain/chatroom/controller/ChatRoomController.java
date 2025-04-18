package com.dongguk.chat.domain.chatroom.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.chatroom.dto.ChatRoomCreateDto;
import com.dongguk.chat.domain.chatroom.service.ChatService;
import com.dongguk.chat.domain.message.dto.MessageSendDto;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/chatroom/create")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomCreateDto dto) {
        Optional<ChatRoom> existingRoom = chatService.find1to1Room(dto.getMyId(), dto.getPartnerId());
        if (existingRoom.isPresent()) {
            return ResponseEntity.ok(existingRoom.get());
        }
        ChatRoom room = chatService.create1to1Room(dto.getMyId(), dto.getPartnerId(), dto.getRoomName());
        return ResponseEntity.ok(room);
    }

    //roomId에 따라 메세지 조회 api
    @GetMapping("/chatroom/{roomId}/messages")
    public List<MessageSendDto> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }

}
