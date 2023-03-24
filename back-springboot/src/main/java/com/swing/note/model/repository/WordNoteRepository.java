package com.swing.note.model.repository;

import com.swing.note.model.entity.WordNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordNoteRepository extends JpaRepository<WordNote, Integer> {
	@Query(value = "SELECT * FROM word_note WHERE user_id = :userId", nativeQuery = true)
	List<WordNote> findAllByUser_UserId (@Param("userId") String userId);
	
	@Query(value = "SELECT * FROM word_note WHERE user_id = :userId ORDER BY RAND() LIMIT 5", nativeQuery = true)
	List<WordNote> findFiveByUser_UserId (@Param("userId") String userId);
	
	WordNote findByUser_UserIdAndWord_WordId (String userId, int wordId);
	
	WordNote findByWordNoteId (int wordNoteId);
}
