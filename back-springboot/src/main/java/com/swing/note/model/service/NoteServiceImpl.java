package com.swing.note.model.service;

import com.swing.note.model.dto.GetWordNoteDto;
import com.swing.note.model.entity.WordNote;
import com.swing.note.model.repository.SentenceNoteRepository;
import com.swing.note.model.repository.WordNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private WordNoteRepository wordNoteRepository;
	
	@Autowired
	private SentenceNoteRepository sentenceNoteRepository;
	
	@Override
	public List<GetWordNoteDto> getWords (String userId, int key) {
		List<WordNote> wordNoteList;
		
		if (key == 0) wordNoteList = wordNoteRepository.findAllByUser_UserId(userId);
		else wordNoteList = wordNoteRepository.findFiveByUser_UserId(userId);
		
		// WordNote -> WordNoteDto 로 변환 후 반환
		return wordNoteList.stream().map(x -> GetWordNoteDto.toDto(x)).collect(toList());
	}
	
}
