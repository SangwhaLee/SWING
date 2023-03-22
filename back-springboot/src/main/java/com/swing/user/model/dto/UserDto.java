package com.swing.user.model.dto;

import com.swing.doodle.model.dto.GameDto;
import com.swing.doodle.model.dto.HistoryDto;
import com.swing.doodle.model.dto.RoomDto;
import com.swing.doodle.model.dto.UserGameDto;
import com.swing.five.model.dto.FiveRankDto;
import com.swing.note.model.dto.SentenceNoteDto;
import com.swing.note.model.dto.WordNoteDto;
import com.swing.sentency.model.dto.SentencyRankDto;
import com.swing.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class UserDto {
	private String userId;
	private String nickname;
	private String profileImageUrl;
	private int sentencyCnt;
	private int fiveCnt;
	private int coupon;
	private int first;
	private FiveRankDto fiveRank;
	private SentencyRankDto sentencyRank;
	private List<WordNoteDto> wordNoteList;
	private List<SentenceNoteDto> sentenceNoteList;
	private List<GameDto> gameList;
	
	public static UserDto toDto(User user) {

		List<WordNoteDto> wordNoteList = new ArrayList<>();
		user.getWordNoteList().forEach(x -> wordNoteList.add(WordNoteDto.toDto(x)));
		List<SentenceNoteDto> sentenceNoteList = new ArrayList<>();
		user.getSentenceNoteList().forEach(x -> sentenceNoteList.add(SentenceNoteDto.toDto(x)));
		List<GameDto> gameList = new ArrayList<>();
		user.getUserGameList().forEach(x -> gameList.add(GameDto.toDto(x.getGame())));
		
		return new UserDto(
				user.getUserId(),
				user.getNickname(),
				user.getProfileImageUrl(),
				user.getSentencyCnt(),
				user.getFiveCnt(),
				user.getCoupon(),
				0,
				FiveRankDto.toDto(user.getFiveRank()),
				SentencyRankDto.toDto(user.getSentencyRank()),
				wordNoteList,
				sentenceNoteList,
				gameList
		);
	}
}
