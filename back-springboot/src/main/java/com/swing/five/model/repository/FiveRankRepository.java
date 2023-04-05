package com.swing.five.model.repository;

import com.swing.five.model.entity.FiveRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiveRankRepository extends JpaRepository<FiveRank, Integer> {
	List<FiveRank> findTop7ByOrderByScoreDesc ();
	
	FiveRank findByUser_UserId (String userId);
}
