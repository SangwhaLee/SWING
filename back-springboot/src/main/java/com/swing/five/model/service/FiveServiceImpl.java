package com.swing.five.model.service;

import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.FiveRankRepository;
import com.swing.five.model.repository.WordRepository;
import com.swing.note.model.repository.WordNoteRepository;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FiveServiceImpl implements FiveService {
	
	@Autowired
	private S3Upload s3Upload;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private FiveRankRepository fiveRankRepository;
	
	@Autowired
	private WordNoteRepository wordNoteRepository;
	
	@Override
	public Word image (MultipartFile multipartFile, String content, String meaningKr, String meaningEn) throws IOException {
		if (wordRepository.findByMeaningKr(meaningKr) == null) return null;
		else {
			Word word = new Word();
			String url = s3Upload.uploadFiles(multipartFile, "images");
			word.setWordImageUrl(url);
			word.setMeaningKr(meaningKr);
			word.setMeaningEn(meaningEn);
			return wordRepository.save(word);
		}
	}
	
	@Override
	public FiveRank saveResult (String userId, Integer score) {
		FiveRank five = new FiveRank();
		five.setUser(userRepository.findByUserId(userId));
		five.setScore(score);
		return fiveRankRepository.save(five);
	}
	
}
