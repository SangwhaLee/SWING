package com.swing.user.model.service;

import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.FiveStat;
import com.swing.five.model.repository.FiveRankRepository;
import com.swing.five.model.repository.FiveStatRepository;
import com.swing.sentency.model.entity.SentencyRank;
import com.swing.sentency.model.repository.SentencyRankRepository;
import com.swing.user.model.dto.ModifyDto;
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
	//새로 가입했을 때 사용할 기본 이미지
	private final String DEFAULT_IMAGE_URL = "https://a405-swing.s3.ap-northeast-2.amazonaws.com/images/profile/default.png";
	@Autowired
	private S3Upload s3Upload;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	FiveRankRepository fiveRankRepository;
	@Autowired
	FiveStatRepository fiveStatRepository;
	@Autowired
	SentencyRankRepository sentencyRankRepository;
	
	@Override
	public UserDto socialLogin(UserDto userDto, String refreshToken) {

		User user = userRepository.findByUserId(userDto.getUserId());

		if(user==null) { //회원이 아닌 경우 새로 가입한다.

			user = new User();
			// User Build
			user.setUserId(userDto.getUserId());
			user.setNickname(userDto.getUserId());
			user.setSentencyCnt(3);
			user.setFiveCnt(1);
			user.setProfileImageUrl(DEFAULT_IMAGE_URL);
			user.setRefreshToken(refreshToken);
			
			user = userRepository.save(user);
			
			FiveRank fiveRank = new FiveRank();
			fiveRank.setUser(user);
			fiveRank.setScore(0);
			
			fiveRankRepository.save(fiveRank);
			
			SentencyRank sentencyRank = new SentencyRank();
			sentencyRank.setUser(user);
			sentencyRank.setScore(0);
			
			sentencyRankRepository.save(sentencyRank);
			
			FiveStat fiveStat = new FiveStat();
			fiveStat.setUser(user);
			fiveStat.setStreak(0);
			fiveStat.setTotalTry(0);
			fiveStat.setTotalScore(0);
			fiveStat.setTotalCorrect(0);
	
			fiveStatRepository.save(fiveStat);
		}
		else{
			user.setRefreshToken(refreshToken);
			userRepository.save(user);
		}

		return UserDto.toDto(user);
	}

	@Override
	public UserDto getUserInfo(String userId) {
		User user = userRepository.findByUserId(userId);
		
		return user == null ? null : UserDto.toDto(userRepository.findByUserId(userId));
	}
	
	@Override
	public UserDto setUserInfo(ModifyDto modifyDto, MultipartFile image) throws IOException {
		User user = userRepository.findByUserId(modifyDto.getUserId());

		if(image!=null && image.getSize()!=2){ // 프로필 이미지가 변경된 경우
			String url = upload(image);
			
			user.setProfileImageUrl(url);
		}
		
		if(modifyDto.getNickname()!=null){ // 닉네임이 변경된 경우
			user.setNickname(modifyDto.getNickname());
		}
		
		if(modifyDto.isDefaultImage()){  // 기본 이미지로 설정하는 경우
			user.setProfileImageUrl(DEFAULT_IMAGE_URL);
		}
		
		return	UserDto.toDto(userRepository.save(user));
	}
	
	@Override
	public boolean checkDuplicate(String nickname) {
		
		return !userRepository.existsByNickname(nickname);
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
	public String getRefreshToken(String userId) {
		User user = userRepository.findByUserId(userId);
		
		return user != null ? user.getRefreshToken() : null;
	}
	
	@Override
	public void delRefreshToken(String userId) {
		User user = userRepository.findByUserId(userId);
		user.setRefreshToken(null);
		
		userRepository.save(user);
	}
	
	@Override
	public String upload (MultipartFile image) throws IOException {
		return s3Upload.uploadFiles(image, "images/profile");
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
	
	@Override
	public int getCouponCnt(String userId) {
		User user = userRepository.findByUserId(userId);
		
		return user.getCoupon();
	}
}
