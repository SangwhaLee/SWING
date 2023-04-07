package com.swing.user.model.repository;

import com.swing.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserId (String userId);
	
	boolean existsByNickname(String nickname);
}
