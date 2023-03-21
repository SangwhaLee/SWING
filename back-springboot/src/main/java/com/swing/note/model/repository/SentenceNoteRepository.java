package com.swing.note.model.repository;

import com.swing.note.model.entity.SentenceNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceNoteRepository extends JpaRepository<SentenceNote, Integer> {
}
