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

	Integer historyId;
	String nickname;
	String profileImageUrl;
	double time;
	String gameImageUrl;
	
	public static HistoryDto toDto(History history){
		return new HistoryDto(
				history.getHistoryId(),
				history.getUser().getNickname(),
				history.getUser().getProfileImageUrl(),
				history.getTime(),
				history.getGameImageUrl()
				);
	}
}
