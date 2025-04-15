package com.dongguk.chat.domain.friend;

import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester; // 친구 요청을 보낸 사람

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend; // 친구가 된 사람

    private String status; // ACTIVE, BLOCKED, DELETED 등 상태 관리용
}

