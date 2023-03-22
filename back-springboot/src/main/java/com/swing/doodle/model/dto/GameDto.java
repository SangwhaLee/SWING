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
	private Integer gameId;
	private RoomDto room;
	private List<RoundDto> roundList = new ArrayList<>();
	private List<UserGameDto> userGameList = new ArrayList<>();
	private DateTime playTime;
}
