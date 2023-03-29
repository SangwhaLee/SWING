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
	public UserDto getUserInfo(String userId) {
		User user = userRepository.findByUserId(userId);
		
		return user == null ? null : UserDto.toDto(userRepository.findByUserId(userId));
	}
	
	@Override
	public boolean deleteUser(String userId) {
		User user = userRepository.findByUserId(userId);
		if(user==null)
			return false;
		userRepository.delete(user);
		return true;
	}
	
	@Override
	public String upload (MultipartFile image) throws IOException {
		return uploadFeedImages(image);
	}
	
	@Override
	public User getSentencyCnt(String userId) {
		User user = userRepository.findByUserId(userId);
		return user;
	}
	
	@Override
	public void setSentencyCnt(String userId, int sentencyCnt) {
		User user = userRepository.findByUserId(userId);
		user.setSentencyCnt(sentencyCnt);
		userRepository.save(user);
	}
	
	@Override
	public int getFiveCnt (String userId) {
		User user = userRepository.findByUserId(userId);
		return user == null ? 0 : user.getFiveCnt();
	}
	
	@Override
	public void setFiveCnt (String userId, int fiveCnt) {
		User user = userRepository.findByUserId(userId);
		user.setFiveCnt(fiveCnt);
		userRepository.save(user);
	}
	
	@Override
	public void setCouponCnt(String userId, int couponCnt) {
		User user = userRepository.findByUserId(userId);
		user.setCoupon(couponCnt);
		
		userRepository.save(user);
	}
	
	/**
	 * 이미지 업로드 처리 메서드
	 */
	private String uploadFeedImages (MultipartFile image) throws IOException {
		return s3Upload.uploadFiles(image, "images");
	}
	
}
