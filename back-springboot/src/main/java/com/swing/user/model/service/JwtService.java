package com.swing.user.model.service;

import java.util.Map;

public interface JwtService {
	<T> String createAccessToken(String userId);
	<T> String createAccessToken(String userId, String role);
	<T> String createRefreshToken(String userId);
	<T> String createRefreshToken(String userId, String role);
	<T> String create(String userId, String subject, int expir);
	<T> String create(String userId, String subject, int expir, String role);
	void validateToken(String jwtToken);
	long getUserId(String jwtToken);
	Map<String, Object> get(String key);
}
