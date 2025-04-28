package com.dongguk.chat.domain.user.repository;

import com.dongguk.chat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByUsername(String username);
}
