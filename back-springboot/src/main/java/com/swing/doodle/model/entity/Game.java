package com.swing.doodle.model.entity;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
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
	private int gameId;
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;
	@OneToMany(mappedBy = "game")
	private List<Round> rounds = new ArrayList<>();
	@OneToMany(mappedBy = "game")
	private List<UserGame> userGames = new ArrayList<>();
	private DateTime playTime;
}
