package com.swing.sentency.model.service;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.sentency.model.dto.SentencyRankDto;
import com.swing.sentency.model.entity.Sentence;
import com.swing.sentency.model.entity.SentencyRank;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SentencyService {
	void saveRank (String userId, int score);
	Sentence getSentency();
	
	String upload(MultipartFile multipartFile, int sentenceId) throws IOException;
	List<SentencyRankDto> getRank (String userId);
	
}
