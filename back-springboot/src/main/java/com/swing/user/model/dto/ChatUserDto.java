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
public class ChatUserDto {
	private String userId;
	private String nickname;
	private String profileImageUrl;
	
	public static ChatUserDto toDto(User user) {
		return new ChatUserDto(
				user.getUserId(),
				user.getNickname(),
				user.getProfileImageUrl()
		);
	}
}
