package com.dongguk.chat.domain.friend.repository;

import com.dongguk.chat.domain.friend.FriendShip;

import java.util.List;

public interface FriendShipRepositoryCustom {
    List<FriendShip> findAcceptedByUserId(Long userId);
    List<FriendShip> findReceivedRequests(Long userId);
    FriendShip findRequesterAndReceiver(Long requesterId, Long receiverId);
}
