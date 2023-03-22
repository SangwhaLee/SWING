package com.swing.user.model.service;

import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
	UserDto login(User user) throws IOException;
	String upload (MultipartFile image) throws IOException;
	int getSentencyCnt(String userId) throws IOException;
	void setSentencyCnt(String userId, int sentencyCnt) throws IOException;
}
