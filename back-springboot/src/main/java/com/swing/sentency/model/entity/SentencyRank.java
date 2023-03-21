package com.swing.sentency.model.entity;

import com.swing.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentencyRank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sentencyRankId;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	private int score;
}
