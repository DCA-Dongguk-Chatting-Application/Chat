package com.dongguk.chat.presence;

import com.dongguk.chat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompAuthChannelInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("token");

            if (token != null) {
                try {
                    String userId = jwtUtil.getClaims(token).getSubject();

                    accessor.setUser(() -> userId);
                    log.info("✅ STOMP 인증 성공 - userId: {}", userId);

                    return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                } catch (Exception e) {
                    log.warn("❌ JWT 파싱 실패: {}", e.getMessage());
                    throw new IllegalArgumentException("STOMP CONNECT 인증 실패");
                }
            }
        }

        return message;
    }
}
