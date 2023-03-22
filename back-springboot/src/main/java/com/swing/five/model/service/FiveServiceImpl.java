package com.swing.five.model.service;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.five.model.dto.WordDto;
import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.FiveRankRepository;
import com.swing.five.model.repository.WordRepository;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public List<WordDto> getFive () {
		List<WordDto> wordDtoList = new ArrayList<>();
		wordRepository.findFive().forEach(x -> wordDtoList.add(WordDto.toDto(x)));
		return wordDtoList;
	}
	
	@Override
	public FiveRank saveResult (String userId, int score) {
		FiveRank fiveRank = fiveRankRepository.findByUser_UserId(userId);
		fiveRank.setScore(score);
		return fiveRankRepository.save(fiveRank);
	}
	
	@Override
	public List<FiveRankDto> getRank (String userId) {
		List<FiveRankDto> fiveRankDtoList = new ArrayList<>();
		List<FiveRank> fiveRankList = fiveRankRepository.findTop7ByOrderByScoreDesc();
		fiveRankList.forEach(x -> fiveRankDtoList.add(FiveRankDto.toDto(x)));
		fiveRankDtoList.add(FiveRankDto.toDto(fiveRankRepository.findByUser_UserId(userId)));
		return fiveRankDtoList;
	}
	
	
}
