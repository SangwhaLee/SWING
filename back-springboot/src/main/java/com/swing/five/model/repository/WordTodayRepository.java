package com.swing.five.model.repository;

import com.swing.five.model.entity.WordToday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordTodayRepository extends JpaRepository<WordToday, Integer> {
}
