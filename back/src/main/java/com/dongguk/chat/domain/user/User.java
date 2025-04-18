package com.dongguk.chat.domain.user;


import com.dongguk.chat.domain.profile.UserProfile;
import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 로그인 IDz

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    private String phoneNumber;

    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    public static User register(UserCreateReq userCreateReq, String encodedPassword){
        return User.builder()
                .username(userCreateReq.getUsername())
                .email(userCreateReq.getEmail())
                .password(encodedPassword)
                .phoneNumber(userCreateReq.getPhoneNumber())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private User(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void updateUserProfile(UserProfile profile){
        this.profile = profile;
    }

    public String getUserProfileNickname(){
        return this.getProfile().getNickname();
    }
    public String getUserProfileImg(){
        return this.getProfile().getImageUrl();
    }
}
