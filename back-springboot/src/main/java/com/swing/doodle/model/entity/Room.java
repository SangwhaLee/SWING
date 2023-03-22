package com.swing.doodle.model.entity;

import com.swing.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomId;
	
	private String name;
	
	private int code;
	
	private int closed;
	
	@ManyToOne
	@JoinColumn(name = "leaderId")
	private User leader;
	
	private int mode;
	
	@OneToMany(mappedBy = "room")
	private List<Game> games = new ArrayList<>();
}
