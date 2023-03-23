package com.swing.note.model.service;

import com.swing.note.model.dto.WordNoteDto;

import java.util.List;

public interface NoteService {
	
	List<WordNoteDto> getAll (String userId);
}
