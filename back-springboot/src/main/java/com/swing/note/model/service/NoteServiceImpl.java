package com.swing.note.model.service;

import com.swing.note.model.dto.WordNoteDto;
import com.swing.note.model.entity.WordNote;
import com.swing.note.model.repository.WordNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private WordNoteRepository wordNoteRepository;
	
	@Override
	public List<WordNoteDto> getAll (String userId) {
		List<WordNote> wordNoteList = wordNoteRepository.findAllByUser_UserId(userId);
//		// WordNote -> WordNoteDto 로 변환 후 반환
		return wordNoteList.stream().map(x -> WordNoteDto.toDto(x)).collect(toList());
	}
	
}
