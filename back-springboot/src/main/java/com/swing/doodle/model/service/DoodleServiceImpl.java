package com.swing.doodle.model.service;

import com.swing.doodle.model.dto.*;
import com.swing.doodle.model.entity.Game;
import com.swing.doodle.model.entity.History;
import com.swing.doodle.model.entity.Room;
import com.swing.doodle.model.entity.Round;
import com.swing.doodle.model.repository.GameRepository;
import com.swing.doodle.model.repository.HistoryRepository;
import com.swing.doodle.model.repository.RoomRepository;
import com.swing.doodle.model.repository.RoundRepository;
import com.swing.five.model.entity.Word;
import com.swing.five.model.repository.WordRepository;
import com.swing.user.model.repository.UserRepository;
import com.swing.util.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		return room.getRoomId();
	}
	
	@Override
	public List<RoomDto> getAllRooms () {
		// 모든 방 Entity 조회 후 RoomDTO로 변환 후 리스트로 반환
		return roomRepository.findAll(Sort.by(Sort.Direction.DESC, "roomId")).stream().map(RoomDto::toDto).collect(toList());
	}
	
	@Override
	public List<RoomDto> searchRooms (String type, String keyword) {
		// 검색어로 방 Entity 조회 후 RoomDTO로 변환 후 리스트로 반환
		if ("roomId".equals(type)) return roomRepository.findAllByRoomIdLikeOrderByRoomIdDesc(Integer.parseInt(keyword)).stream().map(RoomDto::toDto).collect(toList());
		else return roomRepository.findAllByNameContainingOrderByRoomIdDesc(keyword).stream().map(RoomDto::toDto).collect(toList());
	}
	
	@Override
	public void deleteRoom (int roomId) {
		roomRepository.delete(roomRepository.findByRoomId(roomId));
	}
	
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
	public void saveRoundResult (SaveRoundResultDto roundResultSaveDto) throws IOException {
		History history = new History();
		history.setUser(userRepository.findByUserId(roundResultSaveDto.getUserId()));
		history.setRound(roundRepository.findByRoundId(roundResultSaveDto.getRoundId()));
		history.setTime(roundResultSaveDto.getTime());
		history.setGameImageUrl(s3Upload.uploadFiles(roundResultSaveDto.getImage(), "images/doodle"));
		historyRepository.save(history);
	}
	
	@Override
	public List<GetRoundResultDto> getRoundResult (int roundId) {
		List<GetRoundResultDto> getRoundResultDtoList = new ArrayList<>();
		List<History> historyList = historyRepository.findAllByRound_RoundId(roundId);
		for (History history : historyList) {
			getRoundResultDtoList.add(new GetRoundResultDto(
					history.getUser().getNickname(),
					history.getUser().getProfileImageUrl(),
					history.getTime(),
					history.getGameImageUrl()
			));
		}
		return getRoundResultDtoList;
	}
	
}
