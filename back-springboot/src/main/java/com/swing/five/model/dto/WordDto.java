package com.swing.five.model.dto;

import com.swing.five.model.entity.Word;
import com.swing.five.model.entity.WordToday;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class WordDto {
	private Integer wordId;
	private String wordImageUrl;
	private String content;
	private String meaningKr;
	private String meaningEn;
	
	public static WordDto toDto(Word word) {
		return new WordDto(
				word.getWordId(),
				word.getWordImageUrl(),
				word.getContent(),
				word.getMeaningKr(),
				word.getMeaningEn()
		);
	}
	
	public static WordDto toDto(WordToday wordToday) {
		return new WordDto(
				wordToday.getWordId(),
				wordToday.getWordImageUrl(),
				wordToday.getContent(),
				wordToday.getMeaningKr(),
				wordToday.getMeaningEn()
		);
	}
}
