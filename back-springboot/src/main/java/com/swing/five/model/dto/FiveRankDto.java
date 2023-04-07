package com.swing.five.model.dto;

import com.swing.five.model.entity.FiveRank;
import com.swing.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class FiveRankDto {
	private Integer fiveRankId;
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private int score;
	
	public static FiveRankDto toDto(FiveRank fiveRank) {
		UserDto userDto = UserDto.toDto(fiveRank.getUser());
		
		return new FiveRankDto(
				fiveRank.getFiveRankId(),
				userDto.getUserId(),
				userDto.getNickname(),
				userDto.getProfileImageUrl(),
				fiveRank.getScore()
		);
	}
}
