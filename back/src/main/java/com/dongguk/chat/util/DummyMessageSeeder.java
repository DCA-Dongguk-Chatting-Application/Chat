//package com.dongguk.chat.util;
//
//import com.dongguk.chat.domain.chatroom.ChatRoom;
//import com.dongguk.chat.domain.chatroom.repository.ChatRoomRepository;
//import com.dongguk.chat.domain.message.Message;
//import com.dongguk.chat.domain.message.infra.MessageDocument;
//import com.dongguk.chat.domain.message.infra.MessageSearchRepository;
//import com.dongguk.chat.domain.message.repository.MessageRepository;
//import com.dongguk.chat.domain.user.User;
//import com.dongguk.chat.domain.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DummyMessageSeeder implements CommandLineRunner {
//    private final ChatRoomRepository chatRoomRepository;
//    private final MessageRepository messageRepository;
//    private final UserRepository userRepository;
//    private final MessageSearchRepository messageSearchRepository;
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
//            message.setContent("테스트 위한 데이터 " + i);
//            message.setSentAt(LocalDateTime.now());
//
//            Message save = messageRepository.save(message);
//            MessageDocument messageDocument = MessageDocument.from(save);
//
//            messageSearchRepository.save(messageDocument);
//            messageSearchRepository.save(MessageDocument.from(save));
//        }
//    }
//}
