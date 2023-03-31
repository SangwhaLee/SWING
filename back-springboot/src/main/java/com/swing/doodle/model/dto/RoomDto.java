package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	private int roomId;
	private String name;
	private String code;
	private int started;
	private String leaderNickname;
	private int mode;
	private int userCnt;
	
	public static RoomDto toDto(Room room){
		return new RoomDto(
				room.getRoomId(),
				room.getName(),
				room.getCode(),
				room.getStarted(),
				room.getLeader().getNickname(),
				room.getMode(),
				1
		);
	}
}
