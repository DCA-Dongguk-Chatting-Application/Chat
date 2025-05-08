package com.dongguk.chat.domain.friend.controller;

import com.dongguk.chat.domain.friend.FriendShip;
import com.dongguk.chat.domain.friend.dto.FriendDto;
import com.dongguk.chat.domain.friend.dto.FriendReq;
import com.dongguk.chat.domain.friend.dto.FriendRequestDto;
import com.dongguk.chat.domain.friend.service.FriendShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "친구 API", description = "친구 목록 조회, 친구 요청 관리 등 친구 기능 제공")
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendShipService friendShipService;

    @Operation(
            summary = "친구 목록 조회",
            description = """
                    현재 로그인한 사용자의 친구 목록을 조회합니다.

                    - 친구 상태가 `FRIEND`인 관계만 반환됩니다.
                    - 친구의 닉네임으로 검색 시, `keyword` 쿼리 파라미터를 함께 전달할 수 있습니다.
                    - `keyword`가 없으면 전체 친구 목록이 반환됩니다.
                    """
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendDto>> getFriendList(
            @Parameter(description = "조회할 사용자의 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "친구 닉네임 검색 키워드 (선택)", example = "철수")
            @RequestParam(required = false) String keyword) {
        if(keyword != null && !keyword.isBlank()){
            return ResponseEntity.ok(friendShipService.findFriendsByKeyword(userId, keyword));
        }

        List<FriendDto> friends = friendShipService.getFriendsList(userId);
        return ResponseEntity.ok(friends);
    }


    @Operation(
            summary = "받은 친구 요청 목록 조회",
            description = """
                    나에게 도착한 친구 요청 목록을 조회합니다.

                    - 요청 상태가 `REQUESTED`인 관계만 반환됩니다.
                    - 요청자는 쿼리 파라미터로 `userId`를 넘겨야 합니다.
                    """
    )
    @GetMapping("/requests/{userId}")
    public ResponseEntity<List<FriendDto>> getReceivedRequests(@PathVariable Long userId) {
        List<FriendDto> requests = friendShipService.getReceivedList(userId);
        return ResponseEntity.ok(requests);
    }


    @Operation(
            summary = "친구 요청 전송",
            description = """
                    다른 사용자에게 친구 요청을 보냅니다.

                    - 요청자는 `requesterId`, 수신자는 `receiverNickName`로 지정합니다.
                    - 요청 상태는 자동으로 `REQUESTED`로 설정됩니다.
                    - 이미 존재하는 요청이나 친구 관계가 있다면 예외 처리 필요합니다.
                    """
    )
    @PostMapping("/request")
    public void sendFriendRequest(@RequestBody FriendReq requestDto) {
        friendShipService.createFriendRequest(
                requestDto.requesterId(),
                requestDto.receiverNickName());
    }
    @Operation(
            summary = "친구 요청 수락",
            description = """
                    다른 사용자의 친구 요청을 보냅니다.

                    - 요청자는 `requesterId`, 수락자는 `receiverId`로 지정합니다.
                    - 요청 상태를 `FRIEND`로 설정합니다.
                    """
    )
    @PostMapping("/accept")
    public void acceptFriendRequest(@RequestBody FriendRequestDto requestDto){
        friendShipService.acceptFriendRequest(requestDto.getRequesterId(), requestDto.getReceiverId());
    }

    @Operation(
            summary = "친구 삭제",
            description = """
                    친구를 삭제합니다,
                    
                    - 친구 관계를 만들떄 사용했던 기본 PK(id)를 검색 후 -> 삭제합니다.
                    """
    )
    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable Long id){
        friendShipService.deleteFriendShip(id);
    }
}