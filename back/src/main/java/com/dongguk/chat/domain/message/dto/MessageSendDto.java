package com.dongguk.chat.domain.message.dto;

import java.time.LocalDateTime;

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
}
