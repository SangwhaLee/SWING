package com.swing.sentency.model.entity;

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
public class Sentence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sentenceId;
	
	private String sentenceImageUrl;
	
	private String content;
	
	private String meaningKr;
}
