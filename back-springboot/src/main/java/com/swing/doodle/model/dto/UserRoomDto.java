package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.UserRoom;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoomDto {
	private int userRoomId;
	private String userId;
	private int roomId;
	
	static UserRoomDto toDto(UserRoom userRoom) {
		return new UserRoomDto(
				userRoom.getUserRoomId(),
				userRoom.getUser().getUserId(),
				userRoom.getRoom().getRoomId()
		);
	}
}
