package com.dongguk.chat.domain.message.service;

import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.message.dto.MessageSendDto;
import com.dongguk.chat.domain.message.infra.MessageDocument;
import com.dongguk.chat.domain.message.infra.MessageSearchRepository;
import com.dongguk.chat.domain.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageSearchRepository messageSearchRepository;
    private ElasticsearchOperations elasticsearchOperations;

    public List<MessageSendDto> findMessageByKeywordJpa(Long roomId, String keyword){
        long start = System.currentTimeMillis();
//        List<MessageDocument> allByKeyword = messageSearchRepository.findByChatRoomIdAndContentContaining(roomId, keyword);
//        System.out.println(messageSearchRepository.count());
//
        List<Message> allByKeyword = messageRepository.findAllByKeyword(roomId,'%' + keyword + '%');
//
//
        long end = System.currentTimeMillis();
        System.out.println("실행 시간(ms): " + (end - start));

        return MessageSendDto.from(allByKeyword);
//        return MessageSendDto.fromDocuments(allByKeyword);
    }


    public List<MessageSendDto> getAllMessage(Long roomId){
        List<Message> allMessage = messageRepository.findByChatRoomId(roomId);
        return MessageSendDto.from(allMessage);
    }
}
