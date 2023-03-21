package com.swing.five.model.service;

import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.Word;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FiveService {
	FiveRank saveResult (String userId, Integer score);
	
	Word image (MultipartFile multipartFile, String content, String meaningKr, String meaningEn) throws IOException;
}
