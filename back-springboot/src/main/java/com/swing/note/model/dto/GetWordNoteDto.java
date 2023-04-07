package com.swing.note.model.dto;

import com.swing.five.model.dto.WordDto;
import com.swing.note.model.entity.WordNote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class GetWordNoteDto {
	private Integer wordNoteId;
	private String content;
	private String meaningKr;
	private String meaningEn;
	private int checked;
	
	public static GetWordNoteDto toDto(WordNote wordNote) {
		WordDto wordDto = WordDto.toDto(wordNote.getWord());
		
		return new GetWordNoteDto(
				wordNote.getWordNoteId(),
				wordDto.getContent(),
				wordDto.getMeaningKr(),
				wordDto.getMeaningEn(),
				wordNote.getChecked()
		);
	}
}
