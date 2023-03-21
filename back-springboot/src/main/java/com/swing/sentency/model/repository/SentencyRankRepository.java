package com.swing.sentency.model.repository;

import com.swing.sentency.model.entity.SentencyRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentencyRankRepository extends JpaRepository<SentencyRank, Integer> {
}
