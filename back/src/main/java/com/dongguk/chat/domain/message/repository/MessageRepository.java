package com.dongguk.chat.domain.message.repository;

import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);

    List<Message> findByChatRoomId(Long chatRoomId);

    @Query("select m from Message m where m.chatRoom.id= :roomId and m.content like CONCAT('%', :content, '%')")
    List<Message> findAllByKeyword(@Param("roomId") Long roomId, @Param("content") String content);
}

