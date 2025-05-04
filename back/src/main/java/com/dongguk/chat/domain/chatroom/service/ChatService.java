package com.dongguk.chat.domain.chatroom.service;


import com.dongguk.chat.domain.chatroom.dto.ChatRoomCreateDto;
import com.dongguk.chat.domain.chatroom.dto.ChatRoomGroupCreateDto;
import com.dongguk.chat.domain.chatroom.dto.ChatRoomUpgradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.chatroom.repository.ChatRoomRepository;
import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.message.dto.MessageSendDto;
import com.dongguk.chat.domain.message.repository.MessageRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Message saveMessage(MessageSendDto dto) {
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));
        User sender = userRepository.findById(dto.getSender())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setContent(dto.getContent());
        message.setSentAt(LocalDateTime.now());

        System.out.println("메세지 저장 : " + message.getContent());

        return messageRepository.save(message);
    }

    public List<MessageSendDto> getMessagesByRoomId(Long roomId) {
        List<Message> messages = messageRepository.findByChatRoomId(roomId);
        return messages.stream()
            .map(msg -> MessageSendDto.builder()
                .roomId(msg.getChatRoom().getId())
                .sender(msg.getSender().getId())
                .content(msg.getContent())
                .sentAt(msg.getSentAt())
                .build())
            .collect(Collectors.toList());
    }

    public ChatRoom create1to1Room(Long myId, Long partnerId, String roomName) {
        User me = userRepository.findById(myId)
                .orElseThrow(() -> new IllegalArgumentException("내 정보 없음"));
        User partner = userRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("상대방 없음"));
    
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.getParticipants().add(me);
        chatRoom.getParticipants().add(partner);
        
        // 채팅방 이름과 1:1 여부 설정
        if (roomName == null || roomName.isBlank()) {
            chatRoom.setRoomName(partner.getUsername());
        } else {
            chatRoom.setRoomName(roomName);
        }
        chatRoom.setGroup(false);
    
        return chatRoomRepository.save(chatRoom);
    }

    // 1대1 채팅방 찾기
    public Optional<ChatRoom> find1to1Room(Long myId, Long partnerId) {
        return chatRoomRepository.find1to1Room(myId, partnerId);
    }

    //유저가 속해있는 채팅방 찾기
    public List<ChatRoom> getMyChatRooms(Long userId) {
        return chatRoomRepository.findChatRoomsByUserId(userId);
    }

    //채팅방 안에 인원 파악
    public List<User> getParticipantInRoom(Long roomId) {
        return chatRoomRepository.findParticipantsByRoomId(roomId);
    }

    //1대1 채팅방을 1대N 채팅방으로 업그레이드
    public ChatRoom upgradeToGroupChatRoom(ChatRoomUpgradeDto dto){
        ChatRoom originalRoom = chatRoomRepository.findById(dto.getOriginalRoomId())
                .orElseThrow(() -> new IllegalArgumentException("기존 채팅방 없음"));

        //기존 참여자 복사
        Set<User> participants = new HashSet<>(originalRoom.getParticipants());

        // 새 참여자 추가
        for (Long userId : dto.getNewUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(" 사용자 없음 "));

            participants.add(user);
        }

        ChatRoom newGroupRoom = new ChatRoom();
        newGroupRoom.setRoomName(dto.getRoomName()); //룸네임 변경 필요
        newGroupRoom.setGroup(true);
        newGroupRoom.setParticipants(participants);

        return chatRoomRepository.save(newGroupRoom);
    }


    // 1대N 채팅방 개설
    public ChatRoom createGroupChatRoom(ChatRoomGroupCreateDto dto) {
        Set<User> participants = new HashSet<>();

        for (Long userId : dto.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
            participants.add(user);
        }

        ChatRoom groupRoom = new ChatRoom();
        groupRoom.setRoomName(dto.getRoomName());
        groupRoom.setGroup(true);
        groupRoom.setParticipants(participants);

        return chatRoomRepository.save(groupRoom);
    }
}
