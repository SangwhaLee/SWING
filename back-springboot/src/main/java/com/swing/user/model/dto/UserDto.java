package com.swing.user.model.dto;

import com.swing.user.model.entity.User;
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
	private int sentencyCnt;
	private int fiveCnt;
	private int coupon;
	
	public static UserDto toDto(User user) {
		return new UserDto(
				user.getUserId(),
				user.getNickname(),
				user.getProfileImageUrl(),
				user.getSentencyCnt(),
				user.getFiveCnt(),
				user.getCoupon()
		);
	}
}
