package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.Round;
import com.swing.five.model.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
	
	private int roundId;
	private GameDto game;
	private int roundNo;
	private WordDto keyword;
	private List<HistoryDto> historyList = new ArrayList<>();
	
	public static RoundDto toDto(Round round){
		List<HistoryDto> historyList = new ArrayList<>();
		round.getHistoryList().forEach(x->historyList.add(HistoryDto.toDto(x)));
		
		return new RoundDto(
				round.getRoundId(),
				GameDto.toDto(round.getGame()),
				round.getRoundNo(),
				WordDto.toDto(round.getWord()),
				historyList
		);
	}
}
