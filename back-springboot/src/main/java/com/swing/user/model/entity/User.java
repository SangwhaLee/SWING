package com.swing.user.model.entity;

import com.swing.doodle.model.entity.History;
import com.swing.doodle.model.entity.Room;
import com.swing.doodle.model.entity.UserGame;
import com.swing.doodle.model.entity.UserRoom;
import com.swing.five.model.entity.FiveRank;
import com.swing.five.model.entity.FiveStat;
import com.swing.note.model.entity.SentenceNote;
import com.swing.note.model.entity.WordNote;
import com.swing.sentency.model.entity.SentencyRank;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
	@Id
	private String userId;
	
	private String nickname;
	
	private String profileImageUrl;
	
	private int sentencyCnt;
	
	private int fiveCnt;
	
	private int coupon;
	private String refreshToken;
	
	@OneToOne(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private FiveRank fiveRank;
	
	@OneToOne(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private SentencyRank sentencyRank;
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private List<WordNote> wordNoteList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private List<SentenceNote> sentenceNoteList = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private List<History> historyList = new ArrayList<>();
	
	@OneToOne(mappedBy = "leader")
	private Room room;
	
	@OneToOne(mappedBy = "user")
	private UserRoom userRoom;
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private List<UserGame> userGameList = new ArrayList<>();
	
	@OneToOne(mappedBy = "user",
			cascade = CascadeType.REMOVE,
			orphanRemoval = true)
	private FiveStat fiveStat;
	
}
