package com.swing.doodle.model.dto;

import com.swing.doodle.model.entity.History;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {

	int historyId;
	String nickname;
	String profileImageUrl;
	int roundNo;
	String gameImageUrl;
	
	public static HistoryDto toDto(History history){
		return new HistoryDto(
				history.getHistoryId(),
				history.getUser().getNickname(),
				history.getUser().getProfileImageUrl(),
				history.getRound().getRoundNo(),
				history.getGameImageUrl()
				);
	}
}
