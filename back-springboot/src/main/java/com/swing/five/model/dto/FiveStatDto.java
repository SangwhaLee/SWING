package com.swing.five.model.dto;

import com.swing.five.model.entity.FiveStat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FiveStatDto {
	private int totalScore;
	private int totalTry;
	private int totalCorrect;
	private int streak;
	
	public static FiveStatDto toDto (FiveStat fiveStat) {
		return new FiveStatDto(
				fiveStat.getTotalScore(),
				fiveStat.getTotalTry(),
				fiveStat.getTotalCorrect(),
				fiveStat.getStreak()
		);
	}
}
