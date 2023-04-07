package com.swing.doodle.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gameId;
	
	private String roomName;
	
	@OneToMany(mappedBy = "game",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Round> rounds = new ArrayList<>();
	
	@OneToMany(mappedBy = "game",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<UserGame> userGames = new ArrayList<>();
	
	private LocalDateTime playTime;
}
