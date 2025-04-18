package com.dongguk.chat.domain.chatroom.repository;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants p1 JOIN cr.participants p2 " +
        "WHERE p1.id = :userId1 AND p2.id = :userId2 AND cr.isGroup = false")
    Optional<ChatRoom> findPrivateRoomWithUsers(Long userId1, Long userId2);

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.participants p1 JOIN cr.participants p2 " +
    "WHERE :myId IN (p1.id) AND :partnerId IN (p2.id) AND cr.isGroup = false")
    Optional<ChatRoom> find1to1Room(@Param("myId") Long myId, @Param("partnerId") Long partnerId);

}
