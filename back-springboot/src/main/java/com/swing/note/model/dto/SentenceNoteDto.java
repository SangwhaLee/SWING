package com.swing.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentenceNoteDto {
	private int sentenceNoteId;
	private String userId;
	private int sentenceId;
	private int check;
}
