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
public class UserRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userRoomId;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;
}
