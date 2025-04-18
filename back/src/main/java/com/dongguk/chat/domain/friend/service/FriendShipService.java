package com.dongguk.chat.domain.friend.service;

import com.dongguk.chat.domain.friend.FriendShip;
import com.dongguk.chat.domain.friend.dto.FriendDto;
import com.dongguk.chat.domain.friend.repository.FriendShipRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendShipRepository friendShipRepository;
    private final UserRepository userRepository;


    public List<FriendDto> getFriendsList(Long userId){
        List<FriendShip> acceptedFriendShips = friendShipRepository.findAcceptedByUserId(userId);

        List<User> friendList = acceptedFriendShips.stream().map(
                friend -> friend.getRequester().getId().equals(userId)
                        ?friend.getReceiver()
                        :friend.getRequester())
                .toList();
        return FriendDto.fromUserList(friendList);
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

    public void createFriendRequest(Long requesterId, Long receiverId){
        User requestUser = userRepository.findById(requesterId).get();
        User receivedUser = userRepository.findById(receiverId).get();

        FriendShip reqFriendShip = FriendShip.create(requestUser, receivedUser);
        friendShipRepository.save(reqFriendShip);
    }
}
