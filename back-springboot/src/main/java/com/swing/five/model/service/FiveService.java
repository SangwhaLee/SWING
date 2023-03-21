package com.swing.five.model.service;

import com.swing.five.model.entity.Five;
import com.swing.note.model.entity.Word;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FiveService {
	Five saveResult (String userId, Integer score);
	
	Word image (MultipartFile multipartFile, String content, String meaningKr, String meaningEn) throws IOException;
}
