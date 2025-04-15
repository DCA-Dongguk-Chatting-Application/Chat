package com.dongguk.chat.domain.user;

import com.dongguk.chat.domain.chatroom.ChatRoom;
import com.dongguk.chat.domain.message.Message;
import com.dongguk.chat.domain.profile.Profile;
import com.dongguk.chat.domain.friend.FriendRelation;
import com.dongguk.chat.domain.friend.FriendRequest;
import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 사용자 로그인 후 도메인 흐름 요약 (순차적으로 설명)
 *
 * 1. User
 *    - 로그인한 사용자는 User 엔티티로 매핑됨.
 *    - 온라인 여부를 isOnline 필드로 표시함.
 *    - 친구 요청과 친구 관계를 FriendRequest, FriendRelation을 통해 관리함.
 *
 * 2. FriendRequest
 *    - 친구 요청을 보낸 사용자(sender), 받은 사용자(receiver)를 저장.
 *    - 상태는 문자열 (REQUESTED, ACCEPTED, DECLINED) 로 구분됨.
 *    - ACCEPTED가 되면 FriendRelation으로 친구 관계가 형성됨.
 *
 * 3. FriendRelation
 *    - 친구 관계를 담당하는 엔티티.
 *    - 요청자와 친구가 된 유저를 저장하고, 연결된 시점과 상태를 함께 관리함.
 *    - 실무에서는 확장성과 상태관리 측면에서 ManyToMany보다 선호됨.
 *
 * 4. Profile
 *    - 로그인한 사용자에게 연결된 1개의 프로필을 통해 프로필 이미지 및 소개 정보를 보여줄 수 있음.
 *
 * 5. ChatRoom
 *    - 사용자는 1:1 또는 그룹 채팅방에 참여 가능.
 *    - 참여자 목록(participants)을 통해 접속자 리스트 조회.
 *
 * 6. Message
 *    - 채팅방 내 메시지 목록을 통해 과거 대화 내용 확인 가능.
 *    - 메시지 전송 시 sender는 로그인 사용자, chatRoom은 현재 채팅방.
 *
 * 7. FileAttachment
 *    - 메시지에 첨부파일이 포함된 경우 이 엔티티에 저장됨.
 *    - 이미지, 문서 등의 파일 전송 기능 제공.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    // 상태 표시 (온라인 -> true, 오프라인 -> boolean 등)
    private boolean isOnline;

    // 친구 목록 (양방향 연결)
    // 친구 관계 목록 (내가 요청한 관계)
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRelation> friends = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> receivedRequests = new ArrayList<>();

    // 친구 요청 목록 (내가 보낸 요청)
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> sentRequests = new ArrayList<>();

    // 사용자의 채팅방 목록
    @ManyToMany(mappedBy = "participants")
    private Set<ChatRoom> chatRooms = new HashSet<>();

    // 사용자 프로필 (1:1 관계)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    // 내가 보낸 메시지들
    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();


    public static User register(UserCreateReq userCreateReq, String encodedPassword){
        return User.builder()
                .username(userCreateReq.getUsername())
                .email(userCreateReq.getEmail())
                .password(encodedPassword)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
