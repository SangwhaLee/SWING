package com.swing.note.model.dto;

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
	private Integer wordId;
	private Integer check;
}
