package com.swing.sentency.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentencyRankDto {
	private int sentencyRankId;
	private String userId;
	private String profileImageUrl;
	private int score;
}
