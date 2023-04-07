package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
	Game findByGameId (int gameId);
}
