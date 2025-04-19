package com.dongguk.chat.domain.chatroom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        chatRoom.setRoomName(partner.getUsername());
        chatRoom.setGroup(false);
    
        return chatRoomRepository.save(chatRoom);
    }

    // 1대1 채팅방 찾기
    public Optional<ChatRoom> find1to1Room(Long myId, Long partnerId) {
        return chatRoomRepository.find1to1Room(myId, partnerId);
    }
}
