package com.dongguk.chat.domain.profile.repository;

import com.dongguk.chat.domain.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
}
