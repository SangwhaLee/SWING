package com.swing.sentency.model.service;

import com.swing.sentency.model.entity.SentencyRank;
import com.swing.sentency.model.repository.SentencyRankRepository;
import com.swing.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentencyServiceImpl implements SentencyService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SentencyRankRepository sentencyRankRepository;
	
	@Override
	public SentencyRank saveResult(String userId, int score) {
		SentencyRank sentencyRank = new SentencyRank();
		sentencyRank.setUser(userRepository.findByUserId(userId));
		sentencyRank.setScore(score);
		return sentencyRankRepository.save(sentencyRank);
	}
	
}
