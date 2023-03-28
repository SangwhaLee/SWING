package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.Game;
import lombok.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
public class GameDto {
	private Integer gameId;
	private String roomName;
	private List<RoundDto> roundList = new ArrayList<>();
	private DateTime playTime;
	
	public static GameDto toDto(Game game){
		List<RoundDto> roundList = new ArrayList<>();
		game.getRounds().forEach(x->roundList.add(RoundDto.toDto(x)));
		
		return new GameDto(
				game.getGameId(),
				game.getRoomName(),
				roundList,
				game.getPlayTime()
		);
	}
}
