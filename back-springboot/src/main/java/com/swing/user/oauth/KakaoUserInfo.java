package com.swing.user.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	
	private String id;
	private Map<String, Object> kakaoAccount;
	
	public KakaoUserInfo(Map<String, Object> attributes, String id ) {
		this.kakaoAccount = attributes;
		this.id = id;
	}
	
	@Override
	public String getProviderId() {
		return id;
	}
	
	@Override
	public String getProvider() {
		return "kakao";
	}
}
