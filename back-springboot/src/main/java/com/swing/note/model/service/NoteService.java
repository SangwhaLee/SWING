package com.swing.note.model.service;

import com.swing.note.model.dto.GetWordNoteDto;

import java.util.List;

public interface NoteService {
	boolean saveWord (String userId, int wordId);
	
	List<GetWordNoteDto> getWords (String userId, int key);
	
	void checkWord (int wordNoteId);
	
	void deleteWord (int wordNoteId);
}
