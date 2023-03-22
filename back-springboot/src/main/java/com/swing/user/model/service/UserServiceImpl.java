package com.swing.user.model.service;

import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private S3Upload s3Upload;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto login(User loginUser) throws IOException {
		
		User user = userRepository.findByUserId(loginUser.getUserId());
		
		if(user==null) { // 새로 가입
			loginUser.setNickname(loginUser.getUserId());
			loginUser.setCoupon(0);
			loginUser.setSentencyCnt(3);
			loginUser.setFiveCnt(1);
			//fiveRank 추가
			//sentencyRank 추가
		}
		
		return null;
	}
	
	@Override
	public String upload (MultipartFile image) throws IOException {
		return uploadFeedImages(image);
	}
	
	@Override
	public int getSentencyCnt(String userId) throws IOException {
		User user = userRepository.findByUserId(userId);
		if(user!=null){
			return user.getSentencyCnt();
		}
		return 0;
	}
	
	@Override
	public void setSentencyCnt(String userId, int sentencyCnt) throws IOException {
		User user = userRepository.findByUserId(userId);
		user.setSentencyCnt(sentencyCnt);
		userRepository.save(user);
	}
	
	/**
	 * 이미지 업로드 처리 메서드
	 */
	private String uploadFeedImages (MultipartFile image) throws IOException {
		return s3Upload.uploadFiles(image, "images");
	}
	
}
