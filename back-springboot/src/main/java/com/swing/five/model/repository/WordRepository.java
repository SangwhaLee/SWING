package com.swing.five.model.repository;

import com.swing.five.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
	Word findByMeaningKr (String meaningKr);
	
	@Query(value = "SELECT * FROM word ORDER BY RAND() LIMIT 5", nativeQuery = true)
	List<Word> findFive ();
	
	Word findByWordId (int wordId);
}
