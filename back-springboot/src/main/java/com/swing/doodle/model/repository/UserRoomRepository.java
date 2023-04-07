package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
	Integer countByRoom_RoomId (Integer roomId);
	
	UserRoom findByUser_UserId (String userId);
	
	List<UserRoom> findAllByRoom_RoomId (int roomId);
	
	void deleteAllByRoom_RoomId (Integer roomId);
	
	void deleteByUser_UserId (String userId);
	
	List<UserRoom> findAllByUser_UserId (String userId);
}
