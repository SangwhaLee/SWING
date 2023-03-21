package com.swing.five.model.repository;

import com.swing.five.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, int> {
	Word findByMeaningKr (String meaningKr);
}
