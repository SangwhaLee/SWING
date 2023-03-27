package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.CreateRoomDto;
import com.swing.doodle.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoodleServiceImpl implements DoodleService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public int createRoom (CreateRoomDto createRoomDto) {
		return 0;
	}
}
