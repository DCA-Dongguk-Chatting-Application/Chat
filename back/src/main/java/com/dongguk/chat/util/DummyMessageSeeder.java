package com.dongguk.chat.util;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.chatroom.repository.ChatRoomRepository;
import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.message.repository.MessageRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

//@Component
//@RequiredArgsConstructor
//public class DummyMessageSeeder implements CommandLineRunner {
//    private final ChatRoomRepository chatRoomRepository;
//    private final MessageRepository messageRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ChatRoom chatRooms = chatRoomRepository.findById(23L).get();
//        User user = userRepository.findById(126L).get();
//
//        for (int i = 0; i < 1000; i++) {
//            Message message = new Message();
//            message.setChatRoom(chatRooms);
//            message.setSender(user);
//            message.setContent("더미 메시지 내용 " + i);
//            messageRepository.save(message);
//        }
//    }
//}
