package com.swing.doodle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomDto {
	private String name;
	private String code;
	private String leader;
	private int mode;
}
