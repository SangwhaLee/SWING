package com.swing.user.model.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface JwtService {
	<T> String createAccessToken(String userId);
	<T> String createAccessToken(String userId, String role);
	<T> String createRefreshToken(String userId);
	<T> String createRefreshToken(String userId, String role);
	<T> String create(String userId, String subject, int expir);
	<T> String create(String userId, String subject, int expir, String role);
	boolean validateToken(String jwtToken);
	String getUserId(String jwtToken);
	Map<String, Object> get(String key);
	Authentication getAuthentication(String token);
	String resolveToken(HttpServletRequest servletRequest);
}
