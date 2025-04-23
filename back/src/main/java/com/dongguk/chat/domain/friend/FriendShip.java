package com.dongguk.chat.domain.friend;

import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 친구 요청, 거절, 대기 상태를 관리하기 위한 VO 객체
 */
@Entity @Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendShip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester; // 친구 요청을 보낸 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver; // 친구 요청을 받은 사람

    /**
     * 친구 상태를 관리하기 위한 enum 객체
     * 친구, 요청됨
     */
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private FriendShip(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
    }

    public static FriendShip create(User requester, User receiver){
        return FriendShip.builder()
                .requester(requester)
                .receiver(receiver)
                .build();
    }

    public void acceptedFriendRequest(){
        this.status = FriendStatus.FRIEND;
    }

    public User findFriendList(Long userId){
        return this.requester.getId().equals(userId) ? this.receiver : this.requester;
    }

    @PrePersist
    protected void setFriendRequestStatus(){
        this.status = FriendStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }

}
