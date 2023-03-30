package com.swing.doodle.model.repository;

import com.swing.doodle.model.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, Integer> {
	UserGame findByUser_UserIdAndGame_GameId (String userId, int gameId);
}
