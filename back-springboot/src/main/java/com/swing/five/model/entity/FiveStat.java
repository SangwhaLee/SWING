package com.swing.five.model.entity;

import com.swing.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FiveStat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fiveStatId;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ColumnDefault("0")
	private int totalScore;
	
	@ColumnDefault("0")
	private int totalTry;
	
	@ColumnDefault("0")
	private int totalCorrect;
	
	@ColumnDefault("0")
	private int streak;
}
