package com.dongguk.chat.domain.message.repository;

import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);

    List<Message> findByChatRoomId(Long chatRoomId);
}

