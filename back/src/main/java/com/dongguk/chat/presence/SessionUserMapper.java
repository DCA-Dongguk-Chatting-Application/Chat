package com.dongguk.chat.presence;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionUserMapper {
    private final Map<String, Long> sessionIdToUsername = new ConcurrentHashMap<>();

    public void register(String sessionId, Long userId){
        sessionIdToUsername.put(sessionId, userId);
    }

    public Long remove(String sessionId){
        return sessionIdToUsername.remove(sessionId);
    }
}
