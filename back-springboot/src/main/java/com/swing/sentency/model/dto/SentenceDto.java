package com.swing.sentency.model.dto;

import com.swing.sentency.model.entity.Sentence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentenceDto {
	private Integer sentenceId;
	private String sentenceImageUrl;
	private String content;
	private String meaningKr;
	
	public static SentenceDto toDto(Sentence sentence) {
		return new SentenceDto(
				sentence.getSentenceId(),
				sentence.getSentenceImageUrl(),
				sentence.getContent(),
				sentence.getMeaningKr()
		);
	}
}
