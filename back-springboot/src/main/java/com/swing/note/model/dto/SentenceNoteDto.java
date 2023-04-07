package com.swing.note.model.dto;

import com.swing.note.model.entity.SentenceNote;
import com.swing.sentency.model.dto.SentenceDto;
import com.swing.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentenceNoteDto {
	private Integer sentenceNoteId;
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private Integer sentenceId;
	private String sentenceImageUrl;
	private String content;
	private String meaningKr;
	private int checked;
	
	public static SentenceNoteDto toDto(SentenceNote sentenceNote) {
		UserDto userDto = UserDto.toDto(sentenceNote.getUser());
		SentenceDto sentenceDto = SentenceDto.toDto(sentenceNote.getSentence());
		
		return new SentenceNoteDto(
				sentenceNote.getSentenceNoteId(),
				userDto.getUserId(),
				userDto.getNickname(),
				userDto.getProfileImageUrl(),
				sentenceDto.getSentenceId(),
				sentenceDto.getSentenceImageUrl(),
				sentenceDto.getContent(),
				sentenceDto.getMeaningKr(),
				sentenceNote.getChecked()
		);
	}
}
