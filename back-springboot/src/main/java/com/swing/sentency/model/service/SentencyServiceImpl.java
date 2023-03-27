package com.swing.sentency.model.service;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.five.model.entity.FiveRank;
import com.swing.sentency.model.dto.SentencyRankDto;
import com.swing.sentency.model.entity.Sentence;
import com.swing.sentency.model.entity.SentencyRank;
import com.swing.sentency.model.repository.SentenceRepository;
import com.swing.sentency.model.repository.SentencyRankRepository;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SentencyServiceImpl implements SentencyService {
	@Autowired
	private S3Upload s3Upload;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SentencyRankRepository sentencyRankRepository;
	
	@Autowired
	private SentenceRepository sentenceRepository;
	
	@Override
	public Sentence getSentency(){
		int r = (int)Math.floor(Math.random()*78);
		
		Sentence sentence = sentenceRepository.findBySentenceId(r);
		
		return sentence;
	}
	
	@Override
	public String upload(MultipartFile multipartFile, int sentenceId) throws IOException{
		String url = uploadSentenceImages(multipartFile);
		Sentence sentence = sentenceRepository.findBySentenceId(sentenceId);
		sentence.setSentenceImageUrl(url);
		sentenceRepository.save(sentence);
		return url;
	}
	
	/**
	 * 이미지 업로드 처리 메서드
	 */
	private String uploadSentenceImages (MultipartFile image) throws IOException {
		return s3Upload.uploadFiles(image, "images/sentence");
	}
	
	@Override
	public SentencyRank saveResult(String userId, int score) {
		SentencyRank sentencyRank = sentencyRankRepository.findByUser_UserId(userId);
		if(sentencyRank==null){
			User user = userRepository.findByUserId(userId);
			sentencyRank = new SentencyRank();
			sentencyRank.setUser(user);
			sentencyRank.setScore(score);
//			user.setSentencyRank(sentencyRank);
//			userRepository.save(user);
			return sentencyRankRepository.save(sentencyRank);
		}
		else if(sentencyRank.getScore()<score){
			sentencyRank.setScore(score);
//			User user = userRepository.findByUserId(userId);
//			user.setSentencyRank(sentencyRank);
//			userRepository.save(user);
			return sentencyRankRepository.save(sentencyRank);
		}
		return sentencyRank;
	}
	@Override
	public List<SentencyRankDto> getRank (String userId) {
		List<SentencyRankDto> sentencyRankDtoList = new ArrayList<>();
		List<SentencyRank> sentencyRankList = sentencyRankRepository.findTop7ByOrderByScoreDesc();
		sentencyRankList.forEach(x -> sentencyRankDtoList.add(SentencyRankDto.toDto(x)));
		sentencyRankDtoList.add(SentencyRankDto.toDto(sentencyRankRepository.findByUser_UserId(userId)));
		return sentencyRankDtoList;
	}
}
