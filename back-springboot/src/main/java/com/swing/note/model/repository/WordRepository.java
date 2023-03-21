package com.swing.note.model.repository;

import com.swing.note.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
	Word findByMeaningKr (String meaningKr);
}
