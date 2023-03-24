package com.swing.note.model.dto;

import com.swing.five.model.dto.WordDto;
import com.swing.note.model.entity.WordNote;
import com.swing.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class WordNoteDto {
	private Integer wordNoteId;
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private Integer wordId;
	private String wordImageUrl;
	private String content;
	private String meaningKr;
	private String meaningEn;
	private int checked;
	
	public static WordNoteDto toDto(WordNote wordNote) {
		UserDto userDto = UserDto.toDto(wordNote.getUser());
		WordDto wordDto = WordDto.toDto(wordNote.getWord());
		
		return new WordNoteDto(
				wordNote.getWordNoteId(),
				userDto.getUserId(),
				userDto.getNickname(),
				userDto.getProfileImageUrl(),
				wordDto.getWordId(),
				wordDto.getWordImageUrl(),
				wordDto.getContent(),
				wordDto.getMeaningKr(),
				wordDto.getMeaningEn(),
				wordNote.getChecked()
		);
	}
}
