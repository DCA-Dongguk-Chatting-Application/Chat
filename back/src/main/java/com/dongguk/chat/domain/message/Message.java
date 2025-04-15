package com.dongguk.chat.domain.message;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.file.FileAttachment;
import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 메시지 엔티티
 */
@Entity
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime sentAt;

    // 보낸 사용자
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    // 속한 채팅방
    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    // 첨부 파일 (선택적)
    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
    private FileAttachment file;
}
