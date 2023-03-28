package com.swing.doodle.model.entity;

import com.swing.five.model.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Round {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roundId;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private Game game;
	
	private int roundNo;
	
	@ManyToOne
	@JoinColumn(name = "wordId")
	private Word word;
	
	@OneToMany(mappedBy = "round",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<History> historyList = new ArrayList<>();
}
