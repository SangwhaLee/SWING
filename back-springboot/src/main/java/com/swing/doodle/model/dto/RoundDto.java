package com.swing.doodle.model.dto;

import com.swing.five.model.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
	
	private Integer roundId;
	private GameDto game;
	private int roundNo;
	private Word keyword;
	private List<HistoryDto> histories = new ArrayList<>();
}
