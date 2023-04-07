package com.swing.doodle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoundInfoDto {
	private int gameId;
	private int roundId;
	private int roundNo;
	private String content;
	private String meaningKr;
	private String meaningEn;
}
