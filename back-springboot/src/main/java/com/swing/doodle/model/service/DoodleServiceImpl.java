package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.CreateRoomDto;
import com.swing.doodle.model.dto.RoomDto;
import com.swing.doodle.model.entity.Room;
import com.swing.doodle.model.repository.RoomRepository;
import com.swing.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DoodleServiceImpl implements DoodleService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public int createRoom (CreateRoomDto createRoomDto) {
		if (roomRepository.findByLeader_UserId(createRoomDto.getLeaderId()) != null) return -1;
		
		Room room = new Room();
		room.setName(createRoomDto.getName());
		room.setCode(createRoomDto.getCode());
		room.setLeader(userRepository.findByUserId(createRoomDto.getLeaderId()));
		room.setMode(createRoomDto.getMode());
		roomRepository.save(room);
		return room.getRoomId();
	}
	
	@Override
	public List<RoomDto> getAllRooms () {
		// 모든 방 Entity 조회 후 RoomDTO로 변환 후 리스트로 반환
		return roomRepository.findAll().stream().map(RoomDto::toDto).collect(toList());
	}
	
	@Override
	public List<RoomDto> searchRooms (String type, String keyword) {
		// 검색어로 방 Entity 조회 후 RoomDTO로 변환 후 리스트로 반환
		if ("roomId".equals(type)) return roomRepository.findAllByRoomIdLike(Integer.parseInt(keyword)).stream().map(RoomDto::toDto).collect(toList());
		else return roomRepository.findAllByNameContaining(keyword).stream().map(RoomDto::toDto).collect(toList());
	}
}
