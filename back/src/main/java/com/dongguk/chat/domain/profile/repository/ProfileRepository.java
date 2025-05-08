package com.dongguk.chat.domain.profile.repository;

import com.dongguk.chat.domain.profile.UserProfile;
import com.dongguk.chat.domain.user.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
    Profile findByNickname(String nickname);
}
