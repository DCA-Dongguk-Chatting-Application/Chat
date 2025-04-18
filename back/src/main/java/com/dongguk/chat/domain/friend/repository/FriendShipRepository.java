package com.dongguk.chat.domain.friend.repository;

import com.dongguk.chat.domain.friend.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendShipRepository extends JpaRepository<FriendShip, Long>, FriendShipRepositoryCustom {

}
