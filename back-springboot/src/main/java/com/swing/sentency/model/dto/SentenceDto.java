package com.swing.sentency.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentenceDto {
	private int sentenceId;
	private String sentenceImageUrl;
	private String content;
	private String meaning;
}
