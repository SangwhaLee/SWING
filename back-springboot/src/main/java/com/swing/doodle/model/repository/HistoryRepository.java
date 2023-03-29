package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
	List<History> findAllByRound_RoundId (int roundId);
}
