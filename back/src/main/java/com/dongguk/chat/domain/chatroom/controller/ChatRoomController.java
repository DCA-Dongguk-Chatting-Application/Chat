package com.dongguk.chat.domain.chatroom.controller;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dongguk.chat.domain.chatroom.dto.*;
import com.dongguk.chat.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.chatroom.service.ChatService;
import com.dongguk.chat.domain.message.dto.MessageSendDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/chatroom/create")
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomCreateDto dto) {
        Optional<ChatRoom> existingRoom = chatService.find1to1Room(dto.getMyId(), dto.getPartnerId());
        if (existingRoom.isPresent()) {
            return ResponseEntity.ok(ChatRoomResponseDto.from(existingRoom.get()));
        }
        ChatRoom room = chatService.create1to1Room(dto.getMyId(), dto.getPartnerId(), dto.getRoomName());
        return ResponseEntity.ok(ChatRoomResponseDto.from(room));
    }

    //roomId에 따라 메세지 조회 api
    @GetMapping("/chatroom/{roomId}/messages")
    public List<MessageSendDto> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByRoomId(roomId);
    }


    @PostMapping("/chatroom/upgrade")
    public ResponseEntity<ChatRoomResponseDto> upgradeToGroupChat(@RequestBody ChatRoomUpgradeDto dto) {
        ChatRoom newGroupRoom = chatService.upgradeToGroupChatRoom(dto);
        return ResponseEntity.ok(ChatRoomResponseDto.from(newGroupRoom));
    }

    @PostMapping("/chatroom/group")
    public ResponseEntity<ChatRoomResponseDto> createGroupChat(@RequestBody ChatRoomGroupCreateDto request) {
        ChatRoom chatRoom = chatService.createGroupChatRoom(request);
        return ResponseEntity.ok(ChatRoomResponseDto.from(chatRoom));
    }


    //유저가 가지고 있는 채팅방 목록
    @GetMapping("/chatroom/list/{userId}")
    public ResponseEntity<List<ChatRoomResponseDto>> getMyChatRooms(@PathVariable Long userId) {
        List<ChatRoom> myRooms = chatService.getMyChatRooms(userId);
        List<ChatRoomResponseDto> response = myRooms.stream()
                .map(ChatRoomResponseDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    //채팅방 안에 인원 목록
    @GetMapping("/chatroom/{roomId}/participants")
    public ResponseEntity<List<ParticipantDto>> getParticipants(@PathVariable Long roomId) {
        List<User> participants = chatService.getParticipantInRoom(roomId);
        List<ParticipantDto> response = participants.stream()
                .map(ParticipantDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chatroom/{roomId}/add-participants")
    public ResponseEntity<ChatRoomResponseDto> addParticipants(@PathVariable Long roomId, @RequestBody ChatRoomAddparticipantsDto dto){
        ChatRoom updatedRoom = chatService.addParticipantToRoom(roomId, dto);
        return ResponseEntity.ok(ChatRoomResponseDto.from(updatedRoom));
    }
}
