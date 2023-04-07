package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {
	Round findByRoundId (int roundId);
	
	List<Round> findAllByGame_GameIdOrderByRoundId (int gameId);
}
