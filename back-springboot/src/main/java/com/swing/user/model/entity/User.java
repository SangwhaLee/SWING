package com.swing.user.model.entity;

import com.swing.doodle.model.entity.History;
import com.swing.doodle.model.entity.Room;
import com.swing.doodle.model.entity.UserGame;
import com.swing.five.model.entity.FiveRank;
import com.swing.note.model.entity.SentenceNote;
import com.swing.note.model.entity.WordNote;
import com.swing.sentency.model.entity.SentencyRank;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	@Id
	private String userId;
	
	private String nickname;
	
	private String profileImageUrl;
	
	private int sentencyCnt;
	
	private int fiveCnt;
	
	private int coupon;
	
	@OneToOne(mappedBy = "user")
	private FiveRank fiveRank;
	
	@OneToOne(mappedBy = "user")
	private SentencyRank sentencyRank;
	
	@OneToMany(mappedBy = "user")
	private List<WordNote> wordNoteList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<SentenceNote> sentenceNoteList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<History> historyList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<Room> roomList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<UserGame> userGameList = new ArrayList<>();
}
