package com.swing.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ModifyDto { // 유저정보 수정 API에서 사용
	private String userId;
	private String nickname;
	private boolean defaultImage;  //기본이미지로 설정할 것인지
	
}
