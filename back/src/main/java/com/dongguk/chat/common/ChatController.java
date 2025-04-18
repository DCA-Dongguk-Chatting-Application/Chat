package com.dongguk.chat.common;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dongguk.chat.domain.chatroom.service.ChatService;
import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.message.dto.MessageSendDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handle(String message){
        return message;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(MessageSendDto messageDTO) {
        Message saved = chatService.saveMessage(messageDTO);
        messagingTemplate.convertAndSend(
            "/topic/chatroom/" + messageDTO.getRoomId(), 
            MessageSendDto.builder()
                .roomId(saved.getChatRoom().getId())
                .sender(saved.getSender().getId())
                .content(saved.getContent())
                .sentAt(saved.getSentAt())
                .build()
        );
    }
}
