package com.swing.note.model.entity;

import com.swing.five.model.entity.Word;
import com.swing.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WordNote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wordNoteId;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "wordId")
	private Word word;
	
	private int check;
}
