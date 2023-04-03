package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.*;
import com.swing.user.model.dto.ChatUserDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DoodleService {
	int createRoom (CreateRoomDto createRoomDto);
	
	List<RoomDto> getAllRooms ();
	
	List<RoomDto> searchRooms (String type, String keyword);
	
	void deleteRoom (int roomId);
	
	int modifyMode (int roomId, int mode);
	
	int lockRoom (int roomId);
	
	List<RoundInfoDto> getFive (String roomName);
	
	void saveRoundResult (SaveRoundResultDto roundResultSaveDto) throws IOException;
	
	List<GetRoundResultDto> getRoundResult (int roundId);
	
	void saveGameResult (String userId, int gameId, int rank);
	
	Map<String, Object> getGameResult (String userId, int gameId);
	
	List<GetGameHistoryDto> getGameHistory (String userId);
	
	ChatUserDto enterRoom (int roomId, String userId);
	
	List<ChatUserDto> getRoomUsers (int roomId);
	
	RoomInfoDto getRoomInfo (int roomId);
	
	void leaveRoom(String userId);
}
