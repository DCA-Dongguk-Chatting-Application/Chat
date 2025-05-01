package com.dongguk.chat.domain.user.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUserTracker {
    private final Set<Long> onlineUsers = ConcurrentHashMap.newKeySet();

    public void markOnline(Long userId){
        onlineUsers.add(userId);
    }

    public void markOffline(Long userId){
        onlineUsers.remove(userId);
    }

    public boolean isOnline(Long userId){
        return onlineUsers.contains(userId);
    }
}
