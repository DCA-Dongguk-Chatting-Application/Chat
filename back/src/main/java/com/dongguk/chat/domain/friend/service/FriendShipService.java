package com.dongguk.chat.domain.friend.service;

import com.dongguk.chat.domain.friend.FriendShip;
import com.dongguk.chat.domain.friend.dto.FriendDto;
import com.dongguk.chat.domain.friend.repository.FriendShipRepository;
import com.dongguk.chat.domain.profile.UserProfile;
import com.dongguk.chat.domain.profile.repository.ProfileRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import com.dongguk.chat.presence.OnlineUserTracker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendShipRepository friendShipRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final OnlineUserTracker onlineUserTracker;


    /*
    이미 친구 상태인 FriendShip 목록을 불러오기 때문에, userId는 친구 요청 or 받은 사람이다.
    그러므로, Response {requester : 사용자 -> receiver || receiver : 사용자 -> requester} 반환
     */
    @Transactional(readOnly = true)
    public List<FriendDto> getFriendsList(Long userId){
        List<User> friendList = getOtherUsersFromAcceptedFriendships(userId);
        List<FriendDto> friendDtos = FriendDto.fromUserList(friendList);

        return friendDtos.stream().map(friend -> {
            friend.setOnline(onlineUserTracker.isOnline(friend.getUserId()));
            return friend;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<FriendDto> findFriendsByKeyword(Long userId, String keyword){
        List<User> friendList = getOtherUsersFromAcceptedFriendships(userId);
        List<User> findFriend = friendList.stream().filter(friend -> friend.getUserProfileNickname().contains(keyword)).toList();
        return FriendDto.fromUserList(findFriend);
    }

    public List<FriendDto> getReceivedList(Long userId){
        List<FriendShip> receivedRequests = friendShipRepository.findReceivedRequests(userId);

        List<User> reqUsers = receivedRequests.stream().filter(request -> request.getReceiver().getId().equals(userId))
                .map(FriendShip::getRequester)
                .toList();

        return FriendDto.fromUserList(reqUsers);
    }

    public void acceptFriendRequest(Long requesterId, Long receiverId){
        FriendShip findFriendRequest = friendShipRepository.findRequesterAndReceiver(requesterId, receiverId);
        findFriendRequest.acceptedFriendRequest();
        friendShipRepository.save(findFriendRequest);
    }

    public void createFriendRequest(Long requesterId, String receiverNickname){
        User requestUser = userRepository.findById(requesterId).get();

        UserProfile receivedUserProfile = profileRepository.findByNickname(receiverNickname);
        User receivedUser = userRepository.findByProfile(receivedUserProfile);

        FriendShip reqFriendShip = FriendShip.create(requestUser, receivedUser);
        friendShipRepository.save(reqFriendShip);
    }

    @Transactional
    public void deleteFriendShip(Long id){
        friendShipRepository.deleteById(id);
    }

    private List<User> getOtherUsersFromAcceptedFriendships(Long userId){
        List<FriendShip> acceptedFriendShips = friendShipRepository.findAcceptedByUserId(userId);
        List<User> friendList = acceptedFriendShips.stream().map(
                        friend -> friend.findFriendList(userId))
                .toList();
        return friendList;
    }
}
