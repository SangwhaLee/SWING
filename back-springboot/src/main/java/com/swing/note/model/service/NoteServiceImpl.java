package com.swing.note.model.service;

import com.swing.five.model.repository.WordRepository;
import com.swing.note.model.dto.GetSentenceNoteDto;
import com.swing.note.model.dto.GetWordNoteDto;
import com.swing.note.model.entity.SentenceNote;
import com.swing.note.model.entity.WordNote;
import com.swing.note.model.repository.SentenceNoteRepository;
import com.swing.note.model.repository.WordNoteRepository;
import com.swing.sentency.model.repository.SentenceRepository;
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
	private SentenceRepository sentenceRepository;
	
	@Autowired
	private WordNoteRepository wordNoteRepository;
	
	@Autowired
	private SentenceNoteRepository sentenceNoteRepository;
	
	/*
	*******************
	** APIs for Word **
	*******************
	*/
	
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
		return wordNoteList.stream().map(GetWordNoteDto::toDto).collect(toList());
	}
	
	@Override
	public void checkWord (int wordNoteId) {
		WordNote wordNote = wordNoteRepository.findByWordNoteId(wordNoteId);
		wordNote.setChecked(wordNote.getChecked() == 0 ? 1 : 0);
		wordNoteRepository.save(wordNote);
	}
	
	@Override
	public void deleteWord (int wordNoteId) {
		wordNoteRepository.delete(wordNoteRepository.findByWordNoteId(wordNoteId));
	}
	
	/*
	 ***********************
	 ** APIs for Sentence **
	 ***********************
	 */
	
	@Override
	public boolean saveSentence (String userId, int sentenceId) {
		if (sentenceNoteRepository.findByUser_UserIdAndSentence_SentenceId(userId, sentenceId) != null) return false;
		
		SentenceNote sentenceNote = new SentenceNote();
		sentenceNote.setUser(userRepository.findByUserId(userId));
		sentenceNote.setSentence(sentenceRepository.findBySentenceId(sentenceId));
		sentenceNoteRepository.save(sentenceNote);
		return true;
	}
	
	@Override
	public List<GetSentenceNoteDto> getSentences (String userId, int key) {
		List<SentenceNote> sentenceNoteList;
		if (key == 0) sentenceNoteList = sentenceNoteRepository.findAllByUser_UserId(userId);
		else sentenceNoteList = sentenceNoteRepository.findOneByUser_UserId(userId);
		
		// SentenceNote -> SentenceNoteDto 로 변환 후 반환
		return sentenceNoteList.stream().map(GetSentenceNoteDto::toDto).collect(toList());
	}
	
	@Override
	public void checkSentence (int sentenceNoteId) {
		SentenceNote sentenceNote = sentenceNoteRepository.findBySentenceNoteId(sentenceNoteId);
		sentenceNote.setChecked(sentenceNote.getChecked() == 0 ? 1 : 0);
		sentenceNoteRepository.save(sentenceNote);
	}
	
	@Override
	public void deleteSentence (int sentenceNoteId) {
		sentenceNoteRepository.delete(sentenceNoteRepository.findBySentenceNoteId(sentenceNoteId));
	}
	
}
