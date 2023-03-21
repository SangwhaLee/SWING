package com.swing.sentency.model.service;

import com.swing.sentency.model.entity.SentencyRank;

public interface SentencyService {
	SentencyRank saveResult (String userId, int score);
	
}
