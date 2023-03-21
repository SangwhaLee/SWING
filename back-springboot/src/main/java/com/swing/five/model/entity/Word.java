package com.swing.five.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wordId;
	
	private String wordImageUrl;
	
	private String content;
	
	private String meaningKr;
	
	private String meaningEn;
}
