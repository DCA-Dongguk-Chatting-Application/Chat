package com.dongguk.chat.domain.user.repository;

import com.dongguk.chat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
