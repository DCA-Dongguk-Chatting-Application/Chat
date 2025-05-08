package com.dongguk.chat.domain.message.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.message.infra.MessageDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageSendDto {
    private Long roomId;
    private Long sender;
    private String content;
    private LocalDateTime sentAt;

    public static MessageSendDto from(Message message){
        return MessageSendDto.builder()
                .roomId(message.getId())
                .sender(message.getSender().getId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

    public static MessageSendDto from(MessageDocument document){
        return MessageSendDto.builder()
                .roomId(document.getChatRoomId())
                .sender(document.getSenderId())
                .content(document.getContent())
                .sentAt(LocalDate.parse(document.getSentAt()).atStartOfDay())
                .build();
    }

    public static List<MessageSendDto> fromDocuments(List<MessageDocument> documents){
        return documents.stream().map(MessageSendDto::from).toList();
    }

    public static List<MessageSendDto> from(List<Message> messages){
        return messages.stream().map(MessageSendDto::from).toList();
    }
}
