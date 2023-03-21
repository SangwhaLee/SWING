package com.swing.doodle.model.dto;

import com.swing.user.model.entity.User;
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

	User user;

	RoundDto round;
	
	double time;
	
	String gameImageUrl;
	
}
