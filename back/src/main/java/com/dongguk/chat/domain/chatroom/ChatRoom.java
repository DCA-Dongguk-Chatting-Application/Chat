package com.dongguk.chat.domain.chatroom;

import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 채팅방 엔티티 (1:1 또는 그룹 채팅 가능)
 */
@Entity
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private boolean isGroup;

    // 채팅방 참여자
    @ManyToMany
    @JoinTable(name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    // 채팅 메시지 목록
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}