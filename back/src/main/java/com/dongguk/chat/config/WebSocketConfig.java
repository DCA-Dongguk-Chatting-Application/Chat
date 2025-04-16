package com.dongguk.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 클라이언트가 웹소켓 서버에 접속할 수 있도록
     * WebSocket 핸드셰이크 엔드포인트를 설정한다.
     *
     * - 클라이언트는 "/portfolio" 엔드포인트로 WebSocket 연결을 시도하게 된다.
     * - SockJS()는 WebSocket을 지원하지 않는 브라우저를 위한 폴백(fallback) 옵션을 제공한다.
     *   즉, WebSocket이 안 되면 HTTP long polling 등의 대체 수단으로 동작하게 해줌.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/portfolio").withSockJS();
    }
    /**
     * STOMP 메시지 브로커를 설정한다.
     *
     * - 클라이언트가 메시지를 보낼 때 사용하는 경로(prefix)를 지정한다.
     *   → setApplicationDestinationPrefixes("/app")
     *     예: 클라이언트가 "/app/chat.send" 로 메시지를 보내면,
     *     이는 서버 측 @MessageMapping("/chat.send") 메서드로 매핑된다.
     *
     * - 클라이언트가 구독(subscribe)할 수 있는 메시지 브로커 경로를 지정한다.
     *   → enableSimpleBroker("/topic", "/queue")
     *     "/topic" : 다수 사용자에게 브로드캐스트 (그룹 채팅)
     *     "/queue" : 특정 사용자에게 전달 (1:1 채팅)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
    }
}
