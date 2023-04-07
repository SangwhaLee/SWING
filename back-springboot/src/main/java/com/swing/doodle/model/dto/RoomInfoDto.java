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
public class RoomInfoDto {
	private int roomId;
	private String name;
	private String leaderId;
	private String leaderNickname;
	private int mode;
	private int started;
	
	public static RoomInfoDto toDto(Room room){
		return new RoomInfoDto(
				room.getRoomId(),
				room.getName(),
				room.getLeader().getUserId(),
				room.getLeader().getNickname(),
				room.getMode(),
				room.getStarted()
		);
	}
}
