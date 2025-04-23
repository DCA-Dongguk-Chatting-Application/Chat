package com.dongguk.chat.domain.friend.repository;

import com.dongguk.chat.domain.friend.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface FriendShipRepository extends JpaRepository<FriendShip, Long>, FriendShipRepositoryCustom {
}
