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
	private Integer roomId;
	private String name;
	private int code;
	private int started;
	private String leader;
	private int mode;
	
	public static RoomDto toDto(Room room){

		return new RoomDto(
				room.getRoomId(),
				room.getName(),
				room.getCode(),
				room.getStarted(),
				room.getLeader().getNickname(),
				room.getMode()
		);
	}
}
