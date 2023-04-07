package com.swing.five.model.repository;

import com.swing.five.model.entity.FiveStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiveStatRepository extends JpaRepository<FiveStat, Integer> {
	FiveStat findByUser_UserId (String userId);
}
