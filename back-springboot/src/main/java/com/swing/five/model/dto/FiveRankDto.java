package com.swing.five.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FiveRankDto {
	private Integer fiveRankId;
	private String userId;
	private Integer score;
}
