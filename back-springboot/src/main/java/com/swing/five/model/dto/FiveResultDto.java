package com.swing.five.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FiveResultDto {
	private String userId;
	private int dayScore;
	private int dayTry;
	private int dayCorrect;
}
