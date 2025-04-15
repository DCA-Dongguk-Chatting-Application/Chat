package com.dongguk.chat.domain.file;

import com.dongguk.chat.domain.message.Message;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 파일 첨부 (이미지 or 일반 파일)
 */
@Entity
@Getter
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String fileUrl;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;
}

