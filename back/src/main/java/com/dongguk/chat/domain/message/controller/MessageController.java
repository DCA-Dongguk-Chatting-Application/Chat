package com.dongguk.chat.domain.message.controller;

import com.dongguk.chat.domain.message.dto.MessageSendDto;
import com.dongguk.chat.domain.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{roomId}/find")
    @Operation(summary = "채팅방 메시지 키워드 검색", description = "지정한 채팅방에서 키워드를 포함하는 메시지를 조회합니다.," +
            "만약 keyword에 값을 안넣을거나 공백일시 모든 채팅을 반환합니다")
    public ResponseEntity<List<MessageSendDto>> find(
            @Parameter(description = "채팅방 ID", example = "1") @PathVariable Long roomId,
            @Parameter(description = "검색할 키워드", example = "안녕") @RequestParam(required = false) String keyword) {
        if(keyword == null || keyword.isEmpty()){
            return ResponseEntity.ok(messageService.getAllMessage(roomId));
        }else{
            return ResponseEntity.ok(messageService.findMessageByKeywordJpa(roomId, keyword));
        }
    }
}
