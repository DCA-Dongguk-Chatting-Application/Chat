package com.dongguk.chat.domain.friend;

import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

    @Entity @Getter
    public class FriendRequest {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // 요청 보낸 사람
        @ManyToOne
        @JoinColumn(name = "sender_id")
        private User sender;

        // 요청 받은 사람
        @ManyToOne
        @JoinColumn(name = "receiver_id")
        private User receiver;

        // 요청 상태: PENDING, ACCEPTED, DECLINED
        @Enumerated(EnumType.STRING)
        private RequestStatus status;

        public enum RequestStatus {
            PENDING,
            ACCEPTED,
            DECLINED
        }
    }


