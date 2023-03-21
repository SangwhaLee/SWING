package com.swing.user.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
	
	String upload (MultipartFile image) throws IOException;
}
