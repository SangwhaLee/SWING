package com.swing.doodle.model.entity;

import com.swing.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
	
	private String code;
	
	@OneToOne
	@JoinColumn(name = "leaderId")
	private User leader;
	
	private int mode;
	
	@ColumnDefault("0")
	private int started;
	
	@OneToMany(mappedBy = "room",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<UserRoom> userRoomList = new ArrayList<>();
}
