package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.Room;
import com.swing.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	private Integer roomId;
	
	private String name;
	
	private int code;
	private int closed;
	private UserDto leader;
	private int mode;
	private List<GameDto> games = new ArrayList<>();
	
	public static RoomDto toDto(Room room){
		List<GameDto> gamelist = new ArrayList<>();
		return new RoomDto(room.getRoomId(), room.getName(),room.getCode(), room.getClosed(), UserDto.toDto(room.getLeader()), room.getMode(),gamelist);
	}
	
}
