package com.swing.note.model.service;

import com.swing.note.model.dto.GetSentenceNoteDto;
import com.swing.note.model.dto.GetWordNoteDto;

import java.util.List;

public interface NoteService {
	boolean saveWord (String userId, int wordId);
	
	List<GetWordNoteDto> getWords (String userId, int key);
	
	void checkWord (int wordNoteId);
	
	void deleteWord (int wordNoteId);
	
	boolean saveSentence (String userId, int sentenceId);
	
	List<GetSentenceNoteDto> getSentences (String userId, int key);
	
	void checkSentence (int sentenceNoteId);
	
	void deleteSentence (int sentenceNoteId);
}
