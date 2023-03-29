package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.*;

import java.io.IOException;
import java.util.List;

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
}
