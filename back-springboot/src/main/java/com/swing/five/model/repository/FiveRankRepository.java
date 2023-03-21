package com.swing.five.model.repository;

import com.swing.five.model.entity.FiveRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiveRankRepository extends JpaRepository<FiveRank, int> {

}
