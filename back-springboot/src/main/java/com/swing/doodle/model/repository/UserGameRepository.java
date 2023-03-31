package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, Integer> {
	UserGame findByUser_UserIdAndGame_GameId (String userId, int gameId);
	
	@Query(value = "SELECT * FROM user_game WHERE user_id = :userId ORDER BY game_id DESC LIMIT 10", nativeQuery = true)
	List<UserGame> findTop10ByUser_UserIdOrderByGame_GameIdDesc (@Param("userId") String userId);
}
