package com.swing.five.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WordToday {
	@Id
	private Integer wordId;
	
	private String wordImageUrl;
	
	private String content;
	
	private String meaningKr;
	
	private String meaningEn;
}
