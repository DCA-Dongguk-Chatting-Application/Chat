package com.dongguk.chat.domain.message.infra;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;


public interface MessageSearchRepository extends ElasticsearchRepository<MessageDocument, String> {
    List<MessageDocument> findByChatRoomIdAndContentContaining(Long chatRoomId, String keyword);
}
