package com.swing.doodle.model.entity;

import com.swing.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
	
	private String code;
	
	@ColumnDefault("0")
	private int started;
	
	@OneToOne
	@JoinColumn(name = "leaderId")
	private User leader;
	
	private int mode;
	
//	@OneToMany(mappedBy = "room")
//	private List<Game> games = new ArrayList<>();
}
