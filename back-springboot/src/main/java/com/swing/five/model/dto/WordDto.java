package com.swing.five.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class WordDto {
	private int wordId;
	private String wordImageUrl;
	private String content;
	private String meaningKr;
	private String meaningEn;
}
