package com.dongguk.chat.domain.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSendDto {
    private String sender;
    private String content;
}
