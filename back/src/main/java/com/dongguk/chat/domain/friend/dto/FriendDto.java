package com.dongguk.chat.domain.friend.dto;

import com.dongguk.chat.domain.friend.FriendShip;
import com.dongguk.chat.domain.friend.FriendStatus;
import com.dongguk.chat.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 친구 목록을 반환해줄 때, 친구 상태에 따라 구분해주기 위한 dto
 * 친구 상태 = 받은 친구 요청, 친구 추가 완료
 */
@Data
@Builder
public class FriendDto {
    private Long userId;
    private String nickname;
    private String imageUrl;
    private boolean isOnline;

    public static FriendDto fromUser(User user){
        return FriendDto.builder()
                .userId(user.getId())
                .nickname(user.getUserProfileNickname())
                .imageUrl(user.getUserProfileImg())
                .build();
    }

    public static List<FriendDto> fromUserList(List<User> users){
        return users.stream().map(FriendDto::fromUser).toList();
    }
}
