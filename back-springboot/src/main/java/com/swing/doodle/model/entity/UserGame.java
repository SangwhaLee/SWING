package com.swing.doodle.model.entity;

import com.swing.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserGame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userGameId;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private Game game;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
}
