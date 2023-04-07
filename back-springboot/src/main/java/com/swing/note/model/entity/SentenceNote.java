package com.swing.note.model.entity;

import com.swing.sentency.model.entity.Sentence;
import com.swing.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentenceNote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sentenceNoteId;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "sentenceId")
	private Sentence sentence;
	
	@ColumnDefault("0")
	private int checked;
}
