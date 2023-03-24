package com.swing.note.model.repository;

import com.swing.note.model.entity.SentenceNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceNoteRepository extends JpaRepository<SentenceNote, Integer> {
	@Query(value = "SELECT * FROM sentence_note WHERE user_id = :userId", nativeQuery = true)
	List<SentenceNote> findAllByUser_UserId (@Param("userId") String userId);
	
	@Query(value = "SELECT * FROM sentence_note WHERE user_id = :userId ORDER BY RAND() LIMIT 1", nativeQuery = true)
	List<SentenceNote> findOneByUser_UserId (@Param("userId") String userId);
	
	SentenceNote findByUser_UserIdAndSentence_SentenceId (String userId, int sentenceId);
	
	SentenceNote findBySentenceNoteId (int sentenceNoteId);
}
