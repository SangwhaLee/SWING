package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.*;
import com.swing.doodle.model.entity.*;
import com.swing.doodle.model.repository.*;
import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.WordRepository;
import com.swing.user.model.dto.ChatUserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class DoodleServiceImpl implements DoodleService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private RoundRepository roundRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private UserGameRepository userGameRepository;
	
	@Autowired
	private UserRoomRepository userRoomRepository;
	
	@Autowired
	private S3Upload s3Upload;
	
	/*
	 *******************
	 ** APIs for Room **
	 *******************
	 */
	
	@Override
	public int createRoom (CreateRoomDto createRoomDto) {
		if (roomRepository.findByLeader_UserId(createRoomDto.getLeaderId()) != null) return -1;
		
		Room room = new Room();
		room.setName(createRoomDto.getName());
		room.setCode(createRoomDto.getCode());
		room.setLeader(userRepository.findByUserId(createRoomDto.getLeaderId()));
		room.setMode(createRoomDto.getMode());
		roomRepository.save(room);
		
		UserRoom userRoom = new UserRoom();
		userRoom.setRoom(room);
		userRoom.setUser(userRepository.findByUserId(createRoomDto.getLeaderId()));
		userRoomRepository.save(userRoom);
		
		return room.getRoomId();
	}
	
	@Override
	public ChatUserDto enterRoom (int roomId, String userId) {
		userRoomRepository.deleteByUser_UserId(userId);
		
		User user = userRepository.findByUserId(userId);
		Room room = roomRepository.findByRoomId(roomId);
		UserRoom userRoom = new UserRoom();
		userRoom.setRoom(room);
		userRoom.setUser(user);
		userRoomRepository.save(userRoom);
		
		return ChatUserDto.toDto(user);
	}
	
	@Override
	public List<ChatUserDto> getRoomUsers (int roomId, String userId) {
		List<ChatUserDto> chatUserDtoList = new ArrayList<>();
		List<UserRoom> userRoomList = userRoomRepository.findAllByRoom_RoomId(roomId);
		for (UserRoom userRoom : userRoomList) {
			if (!userId.equals(userRoom.getUser().getUserId()))
				chatUserDtoList.add(ChatUserDto.toDto(userRoom.getUser()));
		}
		chatUserDtoList.add(ChatUserDto.toDto(userRepository.findByUserId(userId)));
		
		return chatUserDtoList;
	}
	
	@Override
	public RoomInfoDto getRoomInfo (int roomId) {
		Room room = roomRepository.findByRoomId(roomId);
		return RoomInfoDto.toDto(room);
	}
	
	@Override
	@Transactional
	public void leaveRoom(String userId) {
		Room room = roomRepository.findByLeader_UserId(userId);
		if (room != null) {
			userRoomRepository.deleteAllByRoom_RoomId(room.getRoomId());
			roomRepository.delete(room);
		} else {
			UserRoom userRoom = userRoomRepository.findByUser_UserId(userId);
			userRoomRepository.delete(userRoom);
		}
	}
	
	@Override
	public List<RoomDto> getAllRooms () {
		List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "roomId"));
		
		return roomListToRoomDtoList(roomList);
	}
	
	@Override
	public List<RoomDto> searchRooms (String type, String keyword) {
		List<Room> roomList;
		if ("roomId".equals(type)) roomList =  roomRepository.findAllByRoomIdLikeOrderByRoomIdDesc(Integer.parseInt(keyword));
		else roomList = roomRepository.findAllByNameContainingOrderByRoomIdDesc(keyword);
		
		return roomListToRoomDtoList(roomList);
	}
	
//	DEPRECATED
//	@Override
//	@Transactional
//	public void deleteRoom (int roomId) {
//		roomRepository.delete(roomRepository.findByRoomId(roomId));
//	}
	
	@Override
	public int modifyMode (int roomId, int mode) {
		Room room = roomRepository.findByRoomId(roomId);
		room.setMode(mode);
		roomRepository.save(room);
		return room.getMode();
	}
	
	@Override
	public int lockRoom (int roomId) {
		Room room = roomRepository.findByRoomId(roomId);
		room.setStarted(room.getStarted() == 1 ? 0 : 1);
		roomRepository.save(room);
		return room.getStarted();
	}
	
	/*
	 *******************
	 ** APIs for Game **
	 *******************
	 */
	
	@Override
	public List<RoundInfoDto> getFive (String roomName) {
		// 게임 생성 후 저장
		Game game = new Game();
		game.setRoomName(roomName);
		game.setPlayTime(LocalDateTime.now());
		gameRepository.save(game);
		
		// Round 생성
		// 단어 5개 받아오기
		List<Word> wordList = wordRepository.findFive();
		List<RoundInfoDto> roundInfoDtoList = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Round round = new Round();
			round.setGame(game);
			round.setRoundNo(i);
			round.setWord(wordList.get(i - 1));
			roundRepository.save(round);
			
			roundInfoDtoList.add(new RoundInfoDto(
					game.getGameId(),
					round.getRoundId(),
					i,
					round.getWord().getContent(),
					round.getWord().getMeaningKr(),
					round.getWord().getMeaningEn()
			));
		}
		
		return roundInfoDtoList;
	}
	
	@Override
	public String saveRoundResult (SaveRoundResultDto roundResultSaveDto, MultipartFile image) throws IOException {
		History history = new History();
		history.setUser(userRepository.findByUserId(roundResultSaveDto.getUserId()));
		history.setRound(roundRepository.findByRoundId(roundResultSaveDto.getRoundId()));
		String imageUrl = s3Upload.uploadFiles(image, "images/doodle");
		history.setGameImageUrl(imageUrl);
		historyRepository.save(history);
		return imageUrl;
	}
	
	@Override
	public List<GetRoundResultDto> getRoundResult (int roundId) {
		List<GetRoundResultDto> getRoundResultDtoList = new ArrayList<>();
		List<History> historyList = historyRepository.findAllByRound_RoundId(roundId);
		for (History history : historyList) {
			getRoundResultDtoList.add(new GetRoundResultDto(
					history.getUser().getNickname(),
					history.getUser().getProfileImageUrl(),
					history.getGameImageUrl(),
					history.getRound().getWord().getContent(),
					history.getRound().getWord().getMeaningKr()
			));
		}
		return getRoundResultDtoList;
	}
	
	@Override
	public void saveGameResult (String userId, int gameId, int rank) {
		UserGame userGame = new UserGame();
		userGame.setUser(userRepository.findByUserId(userId));
		userGame.setGame(gameRepository.findByGameId(gameId));
		userGame.setRank(rank);
		userGameRepository.save(userGame);
	}
	
	@Override
	public Map<String, Object> getGameResult (String userId, int gameId) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Round> roundList = roundRepository.findAllByGame_GameIdOrderByRoundId(gameId);
		UserGame userGame = userGameRepository.findByUser_UserIdAndGame_GameId(userId, gameId);
		resultMap.put("rank", userGame.getRank());
		List<List<GetRoundResultDto>> resultList = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			int roundId = roundList.get(i - 1).getRoundId();
			List<GetRoundResultDto> getRoundResultDtoList = historyRepository.
					findAllByRound_RoundId(roundId).
					stream().
					map(GetRoundResultDto::toDto).
					collect(toList());
			resultList.add(getRoundResultDtoList);
		}
		resultMap.put("resultList", resultList);
		
		return resultMap;
	}
	
	@Override
	public List<GetGameHistoryDto> getGameHistory (String userId) {
		List<GetGameHistoryDto> getGameHistoryDtoList = new ArrayList<>();
		List<UserGame> userGameList = userGameRepository.findTop10ByUser_UserIdOrderByGame_GameIdDesc(userId);
		for (UserGame userGame : userGameList) {
			Game game = userGame.getGame();
			getGameHistoryDtoList.add(new GetGameHistoryDto(userGame.getGame().getGameId(), userGame.getRank(), game.getRoomName(), game.getPlayTime()));
		}
		return getGameHistoryDtoList;
	}
	
	private List<RoomDto> roomListToRoomDtoList(List<Room> roomList) {
		List<RoomDto> roomDtoList = new ArrayList<>();
		roomList.forEach(x -> {
			int userCnt = userRoomRepository.countByRoom_RoomId(x.getRoomId());
			RoomDto roomDto = new RoomDto(
					x.getRoomId(),
					x.getName(),
					x.getCode(),
					x.getStarted(),
					x.getLeader().getNickname(),
					x.getMode(),
					userCnt
			);
			roomDtoList.add(roomDto);
		});
		
		return roomDtoList;
	}
	
}
