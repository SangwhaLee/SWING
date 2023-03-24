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
		List<WordDto> wordDtoList = new ArrayList<>();
		wordRepository.findFive().forEach(x -> wordDtoList.add(WordDto.toDto(x)));
		return wordDtoList;
	}
	
	@Override
	public void saveRank (String userId, int dayScore) {
		FiveRank fiveRank = new FiveRank();
		fiveRank.setUser(userRepository.findByUserId(userId));
		fiveRank.setScore(dayScore);
		fiveRankRepository.save(fiveRank);
	}
	
	@Override
	public void saveResult (FiveResultDto fiveResultDto) {
		// save to Five Rank
		FiveRank fiveRank = fiveRankRepository.findByUser_UserId(fiveResultDto.getUserId());
		fiveRank.setScore(fiveRank.getScore());
		fiveRankRepository.save(fiveRank);
		
		// save to Five Stat
		FiveStat oldStat = fiveStatRepository.findByUser_UserId(fiveResultDto.getUserId());
		if (oldStat == null) {
			FiveStat newStat = new FiveStat();
			newStat.setUser(userRepository.findByUserId(fiveResultDto.getUserId()));
			newStat.setTotalScore(fiveResultDto.getDayScore());
			newStat.setTotalTry(fiveResultDto.getDayTry());
			newStat.setTotalCorrect(fiveResultDto.getDayCorrect());
			newStat.setStreak(fiveResultDto.getDayCorrect() == 5 ? 1 : 0);
			fiveStatRepository.save(newStat);
		} else {
			oldStat.setTotalScore(oldStat.getTotalScore() + fiveResultDto.getDayScore());
			oldStat.setTotalTry(oldStat.getTotalTry() + fiveResultDto.getDayTry());
			oldStat.setTotalCorrect(oldStat.getTotalCorrect() + fiveResultDto.getDayCorrect());
			oldStat.setStreak(fiveResultDto.getDayCorrect() == 5 ? oldStat.getStreak() + 1 : 0);
			fiveStatRepository.save(oldStat);
		}
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
