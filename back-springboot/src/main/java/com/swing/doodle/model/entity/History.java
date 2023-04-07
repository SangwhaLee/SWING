package com.swing.doodle.model.entity;

import com.swing.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer historyId;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "roundId")
	private Round round;
	
	private String gameImageUrl;
}
