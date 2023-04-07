package com.swing.five.model.service;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.five.model.dto.FiveResultDto;
import com.swing.five.model.dto.FiveStatDto;
import com.swing.five.model.dto.WordDto;
import com.swing.five.model.entity.Word;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FiveService {
	void saveResult (FiveResultDto fiveResultDto);
	
	Word image (MultipartFile multipartFile, String content, String meaningKr, String meaningEn) throws IOException;
	
	List<FiveRankDto> getRank (String userId);
	
	List<WordDto> getFive ();
	
	FiveStatDto getStat (String userId);
	
	void saveRank (String userId, int dayScore);
}
