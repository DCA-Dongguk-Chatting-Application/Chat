package com.dongguk.chat.domain.profile;

import com.dongguk.chat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 프로필 (사용자당 1개)
 */
@Entity
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
