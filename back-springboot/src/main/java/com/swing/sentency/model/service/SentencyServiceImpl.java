package com.swing.sentency.model.service;

import com.swing.sentency.model.dto.SentencyRankDto;
import com.swing.sentency.model.entity.Sentence;
import com.swing.sentency.model.entity.SentencyRank;
import com.swing.sentency.model.repository.SentenceRepository;
import com.swing.sentency.model.repository.SentencyRankRepository;
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
	private SentencyRankRepository sentencyRankRepository;
	
	@Autowired
	private SentenceRepository sentenceRepository;
	
	@Override
	public Sentence getSentency(){  //게임에 사용할 문장 하나 가져오기
		return sentenceRepository.findSentence();
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
	public void saveRank(String userId, int score) {
		SentencyRank sentencyRank = sentencyRankRepository.findByUser_UserId(userId);
		
		if(sentencyRank.getScore()<score){  //최고점수보다 클 경우만 바꿔서 저장
			sentencyRank.setScore(score);
			sentencyRankRepository.save(sentencyRank);
		}
	}
	
	@Override
	public List<SentencyRankDto> getRank (String userId) {
		List<SentencyRankDto> sentencyRankDtoList = new ArrayList<>();
		
		List<SentencyRank> sentencyRankList = sentencyRankRepository.findTop7ByOrderByScoreDesc();
		
		sentencyRankList.forEach(x -> sentencyRankDtoList.add(SentencyRankDto.toDto(x)));  //TOP 7
		
		sentencyRankDtoList.add(SentencyRankDto.toDto(sentencyRankRepository.findByUser_UserId(userId)));  //내 점수
		
		return sentencyRankDtoList;
	}
	
}
