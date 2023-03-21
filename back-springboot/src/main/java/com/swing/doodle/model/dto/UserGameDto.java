package com.swing.doodle.model.dto;

import com.swing.user.model.entity.User;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserGameDto {

	private int userGameId;

	private GameDto game;
	
	private User user;
	
	
}
