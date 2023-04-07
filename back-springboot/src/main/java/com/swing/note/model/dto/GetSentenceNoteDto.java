package com.swing.note.model.dto;

import com.swing.note.model.entity.SentenceNote;
import com.swing.sentency.model.dto.SentenceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class GetSentenceNoteDto {
	private Integer sentenceNoteId;
	private String sentenceImageUrl;
	private String content;
	private String meaningKr;
	private int checked;
	
	public static GetSentenceNoteDto toDto(SentenceNote sentenceNote) {
		SentenceDto sentenceDto = SentenceDto.toDto(sentenceNote.getSentence());
		
		return new GetSentenceNoteDto(
				sentenceNote.getSentenceNoteId(),
				sentenceDto.getSentenceImageUrl(),
				sentenceDto.getContent(),
				sentenceDto.getMeaningKr(),
				sentenceNote.getChecked()
		);
	}
}
