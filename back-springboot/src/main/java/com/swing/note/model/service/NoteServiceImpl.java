package com.swing.note.model.service;

import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.WordRepository;
import com.swing.note.model.dto.GetWordNoteDto;
import com.swing.note.model.entity.WordNote;
import com.swing.note.model.repository.SentenceNoteRepository;
import com.swing.note.model.repository.WordNoteRepository;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private WordNoteRepository wordNoteRepository;
	
	@Autowired
	private SentenceNoteRepository sentenceNoteRepository;
	
	@Override
	public boolean saveWord (String userId, int wordId) {
		if (wordNoteRepository.findByUser_UserIdAndWord_WordId(userId, wordId) != null) return false;
		
		WordNote wordNote = new WordNote();
		wordNote.setUser(userRepository.findByUserId(userId));
		wordNote.setWord(wordRepository.findByWordId(wordId));
		wordNoteRepository.save(wordNote);
		return true;
	}
	
	@Override
	public List<GetWordNoteDto> getWords (String userId, int key) {
		List<WordNote> wordNoteList;
		
		if (key == 0) wordNoteList = wordNoteRepository.findAllByUser_UserId(userId);
		else wordNoteList = wordNoteRepository.findFiveByUser_UserId(userId);
		
		// WordNote -> WordNoteDto 로 변환 후 반환
		return wordNoteList.stream().map(x -> GetWordNoteDto.toDto(x)).collect(toList());
	}
	
	
}
