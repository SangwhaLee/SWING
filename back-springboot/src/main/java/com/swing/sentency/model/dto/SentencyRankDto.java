package com.swing.sentency.model.dto;

import com.swing.sentency.model.entity.SentencyRank;
import com.swing.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class SentencyRankDto {
	private Integer sentencyRankId;
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private int score;
	
	public static SentencyRankDto toDto(SentencyRank sentencyRank) {
		UserDto userDto = UserDto.toDto(sentencyRank.getUser());
		
		return new SentencyRankDto(
				sentencyRank.getSentencyRankId(),
				userDto.getUserId(),
				userDto.getNickname(),
				userDto.getProfileImageUrl(),
				sentencyRank.getScore()
		);
	}
}
