package com.swing.sentency.model.repository;

import com.swing.sentency.model.entity.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Integer> {
	Sentence findBySentenceId(int sentenceId);
	@Query(value = "SELECT * FROM sentence ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Sentence findSentence();
}
