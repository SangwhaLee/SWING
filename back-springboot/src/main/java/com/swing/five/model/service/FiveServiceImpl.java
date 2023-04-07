package com.swing.five.model.service;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.five.model.dto.FiveResultDto;
import com.swing.five.model.dto.FiveStatDto;
import com.swing.five.model.dto.WordDto;
import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.FiveStat;
import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.FiveRankRepository;
import com.swing.five.model.repository.FiveStatRepository;
import com.swing.five.model.repository.WordRepository;
import com.swing.five.model.repository.WordTodayRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FiveServiceImpl implements FiveService {
	
	@Autowired
	private S3Upload s3Upload;
	
	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private WordTodayRepository wordTodayRepository;
	
	@Autowired
	private FiveRankRepository fiveRankRepository;
	
	@Autowired
	private FiveStatRepository fiveStatRepository;
	
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
		return wordTodayRepository.findAll().stream().map(WordDto::toDto).collect(toList());
	}
	
	@Override
	public void saveRank(String userId, int score) {
		FiveRank fiveRank = fiveRankRepository.findByUser_UserId(userId);
		
		if(fiveRank.getScore()<score){
			fiveRank.setScore(score);
			fiveRankRepository.save(fiveRank);
		}
	}
	
	@Override
	public void saveResult (FiveResultDto fiveResultDto) {
		// save to Five Rank
		FiveRank fiveRank = fiveRankRepository.findByUser_UserId(fiveResultDto.getUserId());
		fiveRank.setScore(fiveRank.getScore());
		fiveRankRepository.save(fiveRank);
		
		// save to Five Stat
		FiveStat stat = fiveStatRepository.findByUser_UserId(fiveResultDto.getUserId());
		stat.setTotalScore(stat.getTotalScore() + fiveResultDto.getDayScore());
		stat.setTotalTry(stat.getTotalTry() + fiveResultDto.getDayTry());
		stat.setTotalCorrect(stat.getTotalCorrect() + fiveResultDto.getDayCorrect());
		stat.setStreak(fiveResultDto.getDayCorrect() == 5 ? stat.getStreak() + 1 : 0);
		fiveStatRepository.save(stat);
	}
	
	@Override
	public List<FiveRankDto> getRank (String userId) {
		List<FiveRankDto> fiveRankDtoList = new ArrayList<>();
		List<FiveRank> fiveRankList = fiveRankRepository.findTop7ByOrderByScoreDesc();
		fiveRankList.forEach(x -> fiveRankDtoList.add(FiveRankDto.toDto(x)));
		fiveRankDtoList.add(FiveRankDto.toDto(fiveRankRepository.findByUser_UserId(userId)));
		return fiveRankDtoList;
	}
	
	@Override
	public FiveStatDto getStat (String userId) {
		return FiveStatDto.toDto(fiveStatRepository.findByUser_UserId(userId));
	}
	
}
