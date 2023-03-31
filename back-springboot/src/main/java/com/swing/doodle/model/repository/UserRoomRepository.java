package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
	Integer countByRoom_RoomId (Integer roomId);
}
