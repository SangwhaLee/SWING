package com.swing.user.model.dto;

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
	private List<FiveRankDto> fiveRankList;
	private List<SentencyRankDto> sentencyRankList;
	private List<WordNoteDto> wordNoteList;
	private List<SentenceNoteDto> sentenceNoteList;
	private List<HistoryDto> historyList;
	private List<RoomDto> roomList;
	private List<UserGameDto> userGameList;
	
	public static UserDto toDto(User user) {
		List<FiveRankDto> fiveRankList = new ArrayList<>();
		user.getFiveRankList().forEach(x -> fiveRankList.add(FiveRankDto.toDto(x)));
		List<SentencyRankDto> sentencyRankList = new ArrayList<>();
		user.getSentencyRankList().forEach(x -> sentencyRankList.add(SentencyRankDto.toDto(x)));
		List<WordNoteDto> wordNoteList = new ArrayList<>();
		user.getWordNoteList().forEach(x -> wordNoteList.add(WordNoteDto.toDto(x)));
		List<SentenceNoteDto> sentenceNoteList = new ArrayList<>();
		user.getSentenceNoteList().forEach(x -> sentenceNoteList.add(SentenceNoteDto.toDto(x)));
		List<HistoryDto> historyList = new ArrayList<>();
		user.getHistoryList().forEach(x -> historyList.add(HistoryDto.toDto(x)));
		List<RoomDto> roomList = new ArrayList<>();
		user.getRoomList().forEach(x -> roomList.add(RoomDto.toDto(x)));
		List<UserGameDto> userGameList = new ArrayList<>();
		user.getUserGameList().forEach(x -> userGameList.add(UserGameDto.toDto(x)));
		
		return new UserDto(
				user.getUserId(),
				user.getNickname(),
				user.getProfileImageUrl(),
				user.getSentencyCnt(),
				user.getFiveCnt(),
				user.getCoupon(),
				0,
				fiveRankList,
				sentencyRankList,
				wordNoteList,
				sentenceNoteList,
				historyList,
				roomList,
				userGameList
		);
	}
}
