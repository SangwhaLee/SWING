package com.swing.doodle.model.dto;

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
	
	private int gameId;
	
	private RoomDto room;
	
	private List<RoundDto> rounds = new ArrayList<>();
	
	private List<UserGameDto> userGames = new ArrayList<>();
	
	private DateTime playTime;
}
