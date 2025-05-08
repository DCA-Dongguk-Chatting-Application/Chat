package com.dongguk.chat.domain.message.infra;

import com.dongguk.chat.domain.message.Message;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDocument {

    @Id
    private String id;

    private String content;

//    @Field(type = FieldType.Date)
    private String sentAt;

    private Long senderId;

    private String senderUsername; // optional: for search/display

    private Long chatRoomId;

    private String chatRoomName; // optional

    private String fileUrl; // if file exists

    private String fileName;

    public static MessageDocument from(Message message) {
        return MessageDocument.builder()
                .id(String.valueOf(message.getId()))
                .content(message.getContent())
                .sentAt(message.getSentAt().toString())
                .senderId(message.getSender().getId())
                .senderUsername(message.getSender().getUsername()) // 선택사항
                .chatRoomId(message.getChatRoom().getId())
                .fileUrl(null)
                .fileName(null)
                .build();
    }
}