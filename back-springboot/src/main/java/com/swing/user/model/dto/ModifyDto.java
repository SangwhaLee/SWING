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
public class ModifyDto {
	private String userId;
	private String nickname;
	private boolean defaultImage;
	
}
