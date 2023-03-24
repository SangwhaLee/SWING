package com.swing.note.model.service;

import com.swing.note.model.dto.GetWordNoteDto;

import java.util.List;

public interface NoteService {
	List<GetWordNoteDto> getWords (String userId, int key);
}
