package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.UserGame;
import com.swing.user.model.dto.UserDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserGameDto {
	private int userGameId;
	private UserDto user;
	private GameDto game;
	private int rank;
	
	public static UserGameDto toDto (UserGame userGame) {
		return new UserGameDto(
				userGame.getUserGameId(),
				UserDto.toDto(userGame.getUser()),
				GameDto.toDto(userGame.getGame()),
				userGame.getRank()
		);
	}
}
