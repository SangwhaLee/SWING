package com.swing.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class UserDto {
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private Integer sentencyCnt;
	private Integer fiveCnt;
	private Integer coupon;
}
