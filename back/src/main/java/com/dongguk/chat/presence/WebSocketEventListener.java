package com.dongguk.chat.presence;

import com.dongguk.chat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final OnlineUserTracker onlineUserTracker;
    private final SessionUserMapper sessionUserMapper;


    @EventListener
    public void onConnect(SessionConnectedEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        log.info("세션에 저장된 attribute = {}", accessor.getSessionAttributes());

        Principal principal = (Principal) event.getMessage().getHeaders().get("simpUser");

        // ✅ ChannelInterceptor에서 넣어준 userId를 꺼냄
        if (principal != null) {
            String userIdStr = accessor.getUser().getName(); // ✅ Principal에서 userId 추출
            Long userId = Long.parseLong(userIdStr);
            log.info("✅ WebSocket 연결 성공 - userId: {}", userId);
            sessionUserMapper.register(sessionId, userId);
            onlineUserTracker.markOnline(userId);
        } else {
            log.warn("❌ WebSocket 연결 실패 - Principal 없음");
        }
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        Long userId = sessionUserMapper.remove(sessionId);
        if (userId != null) {
            System.out.println("👋 사용자 연결 종료 - userId: " + userId);
            onlineUserTracker.markOffline(userId);
        } else {
            System.out.println("⚠️ 연결 종료 - userId를 찾을 수 없음 (이미 제거됨)");
        }
    }
}
