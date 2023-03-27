package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.CreateRoomDto;
import com.swing.doodle.model.dto.RoomDto;
import com.swing.five.model.dto.WordDto;

import java.util.List;

public interface DoodleService {
	int createRoom (CreateRoomDto createRoomDto);
	
	List<RoomDto> getAllRooms ();
	
	List<RoomDto> searchRooms (String type, String keyword);
	
	void deleteRoom (int roomId);
	
	int modifyMode (int roomId, int mode);
	
	int lockRoom (int roomId);
	
	List<WordDto> getFive ();
}
