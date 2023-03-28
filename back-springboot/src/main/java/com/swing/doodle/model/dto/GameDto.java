package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.Game;
import lombok.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
public class GameDto {
	private Integer gameId;
	private RoomDto room;
	private DateTime playTime;
	private List<RoundDto> roundList = new ArrayList<>();
	private List<UserGameDto> userGameDtoList = new ArrayList<>();
	
	public static GameDto toDto(Game game){
		List<RoundDto> roundDtoList = game.getRounds().stream().map(RoundDto::toDto).collect(toList());
		List<UserGameDto> userGameDtoList = game.getUserGames().stream().map(UserGameDto::toDto).collect(toList());
		
		return new GameDto(
				game.getGameId(),
				RoomDto.toDto(game.getRoom()),
				game.getPlayTime(),
				roundDtoList,
				userGameDtoList
		);
	}
}
