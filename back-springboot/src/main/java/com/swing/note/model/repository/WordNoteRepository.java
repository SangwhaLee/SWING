package com.swing.note.model.repository;

import com.swing.note.model.entity.WordNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordNoteRepository extends JpaRepository<WordNote, int> {
}
