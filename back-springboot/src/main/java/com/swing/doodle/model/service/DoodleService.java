package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.CreateRoomDto;
import com.swing.doodle.model.dto.RoomDto;

import java.util.List;

public interface DoodleService {
	int createRoom (CreateRoomDto createRoomDto);
	
	List<RoomDto> getAllRooms ();
}
