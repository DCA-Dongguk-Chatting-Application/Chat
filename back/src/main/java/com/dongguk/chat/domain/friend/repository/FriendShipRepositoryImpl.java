package com.dongguk.chat.domain.friend.repository;

import com.dongguk.chat.domain.friend.FriendShip;
import com.dongguk.chat.domain.friend.FriendStatus;
import com.dongguk.chat.domain.friend.QFriendShip;
import com.dongguk.chat.domain.friend.repository.FriendShipRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FriendShipRepositoryImpl implements FriendShipRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QFriendShip friendShip = QFriendShip.friendShip;

    /**
     * FriendShip 테이블에서, ( 사용자가 요청한 List , 사용자가 받은 List ) 중 FriendStatus 가 ACCEPTED
     * 즉, 친구 요청이 승인된 친구 목록을 추출하기 위한 method
     * @param userId
     * @return
     */
    @Override
    public List<FriendShip> findAcceptedByUserId(Long userId) {
        return queryFactory
                .selectFrom(friendShip)
                .where(
                        friendShip.status.eq(FriendStatus.FRIEND)
                                .and(friendShip.requester.id.eq(userId)
                                        .or(friendShip.receiver.id.eq(userId)))
                )
                .fetch();
    }

    @Override
    public List<FriendShip> findReceivedRequests(Long userId) {
        return queryFactory
                .selectFrom(friendShip)
                .where(
                        friendShip.status.eq(FriendStatus.REQUESTED)
                                .and(friendShip.receiver.id.eq(userId))
                ).fetch();
    }

    @Override
    public FriendShip findRequesterAndReceiver(Long requesterId, Long receiverId) {
        return queryFactory
                .selectFrom(friendShip)
                .where(
                        friendShip.receiver.id.eq(receiverId)
                                .and(friendShip.requester.id.eq(requesterId))
                )
                .fetchOne();
    }

    // 내가 보낸 요청을 취소하려고 한건데, 아직 기능을 추가하진 않아서 만들진 않았음
    @Override
    public List<FriendShip> findSentRequests(Long userId) {
        return null;
    }
}
