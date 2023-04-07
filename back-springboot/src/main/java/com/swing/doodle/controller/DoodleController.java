package com.swing.doodle.controller;

import com.swing.doodle.model.type.MessageType;
import com.swing.doodle.model.dto.*;
import com.swing.doodle.model.service.DoodleService;
import com.swing.user.controller.UserController;
import com.swing.user.model.dto.ChatUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/doodle")
@Api(tags = {"Speedoodle 관리 API"})
public class DoodleController {
	
	@Autowired
	private DoodleService doodleService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/send")
	public void sendMsg(@Payload Map<String,Object> data) {
		if ("ENTER".equals(data.get("messageType").toString())) {
			logger.warn(data.get("userId") + " enter");
			doodleService.enterRoom(Integer.parseInt(data.get("roomId").toString()), data.get("userId").toString());
		} else if ("LEAVE".equals(data.get("messageType").toString())) {
			logger.warn(data.get("userId") + " leave");
			doodleService.leaveRoom(data.get("userId").toString());
		}
		simpMessagingTemplate.convertAndSend("/sub/" + data.get("roomId"), data);
	}
	
	@ApiOperation(value = "방 생성", notes = "방 생성 API", response = Map.class)
	@PostMapping("/room")
	public ResponseEntity<?> createRoom(
			@RequestBody @ApiParam(value = "방 정보") CreateRoomDto createRoomDto) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int roomId = doodleService.createRoom(createRoomDto);
			if (roomId == -1) resultMap.put("message", ALREADY_EXIST);
			else {
				resultMap.put("message", SUCCESS);
				resultMap.put("roomId", roomId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 생성 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
//	DEPRECATED
//	@ApiOperation(value = "방 입장", notes = "방 입장 API", response = Map.class)
//	@PostMapping("/room/enter/{roomId}/{userId}")
//	public ResponseEntity<?> enterRoom(
//			@PathVariable @ApiParam(value = "방 ID") int roomId,
//			@PathVariable @ApiParam(value = "유저 ID") String userId) {
//
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.OK;
//
//		try {
//			// 새로 들어온 사람 정보
//			ChatUserDto newUser = doodleService.enterRoom(roomId, userId);
//			if (newUser == null) resultMap.put("message", ALREADY_EXIST);
//			else {
//				resultMap.put("message", SUCCESS);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("방 입장 실패 : {}", e);
//			resultMap.put("message", FAIL);
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new ResponseEntity<>(resultMap, status);
//
//	}
	
//	DEPRECATED
//	@ApiOperation(value = "방 퇴장", notes = "방 퇴장 API", response = Map.class)
//	@DeleteMapping("/room/leave/{roomId}/{userId}")
//	public ResponseEntity<?> leaveRoom(
//			@PathVariable @ApiParam(value = "방 ID") int roomId,
//			@PathVariable @ApiParam(value = "유저 ID") String userId) {
//
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.OK;
//
//		try {
//			resultMap.put("message", SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("방 퇴장 실패 : {}", e);
//			resultMap.put("message", FAIL);
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new ResponseEntity<>(resultMap, status);
//
//	}
	
	@ApiOperation(value = "방 정보 조회", notes = "방 정보 조회 API", response = Map.class)
	@GetMapping("/room/info/{roomId}/{userId}")
	public ResponseEntity<?> getRoomInfo(
			@PathVariable @ApiParam(value = "방 ID") int roomId,
			@PathVariable @ApiParam(value = "유저 ID") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			RoomInfoDto roomInfoDto = doodleService.getRoomInfo(roomId);
			List<ChatUserDto> chatUserDtoList = doodleService.getRoomUsers(roomId, userId);
			resultMap.put("message", SUCCESS);
			resultMap.put("roomInfo", roomInfoDto);
			resultMap.put("chatUserList", chatUserDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 정보 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "방 전체 조회", notes = "방 전체 조회 API", response = Map.class)
	@GetMapping("/rooms")
	public ResponseEntity<?> getAllRooms() {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<RoomDto> roomDtoList = doodleService.getAllRooms();
			resultMap.put("message", SUCCESS);
			resultMap.put("roomList", roomDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 전체 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "방 검색", notes = "방 검색 API", response = Map.class)
	@GetMapping("/rooms/{type}/{keyword}")
	public ResponseEntity<?> searchRooms(
			@PathVariable @ApiParam(value = "검색어 타입") String type,
			@PathVariable @ApiParam(value = "검색어") String keyword) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<RoomDto> roomDtoList = doodleService.searchRooms(type, keyword);
			resultMap.put("message", SUCCESS);
			resultMap.put("roomList", roomDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 검색 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
//	DEPRECATED
//	@ApiOperation(value = "방 삭제", notes = "방 삭제 API", response = Map.class)
//	@DeleteMapping("/room/{roomId}")
//	public ResponseEntity<?> deleteRoom(
//			@PathVariable @ApiParam(value = "검색어 타입") int roomId) {
//
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.OK;
//
//		try {
//			doodleService.deleteRoom(roomId);
//			resultMap.put("message", SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("방 삭제 실패 : {}", e);
//			resultMap.put("message", FAIL);
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new ResponseEntity<>(resultMap, status);
//
//	}
	
	@ApiOperation(value = "방 모드 변경", notes = "방 모드 변경 API", response = Map.class)
	@PutMapping("/room/{roomId}/{mode}")
	public ResponseEntity<?> modifyMode(
			@PathVariable @ApiParam(value = "방 번호") int roomId,
			@PathVariable @ApiParam(value = "모드") int mode) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int changedMode = doodleService.modifyMode(roomId, mode);
			resultMap.put("message", SUCCESS);
			resultMap.put("mode", changedMode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 모드 변경 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "게임 시작(방 잠금/해제)", notes = "게임 시작(방 잠금/해제) API", response = Map.class)
	@PutMapping("/start/{roomId}")
	public ResponseEntity<?> lockRoom(
			@PathVariable @ApiParam(value = "방 번호") int roomId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int started = doodleService.lockRoom(roomId);
			resultMap.put("message", SUCCESS);
			resultMap.put("started", started);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 잠금/해제 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "게임 시작(라운드 5개 생성)", notes = "게임 시작(라운드 5개 생성) API", response = Map.class)
	@GetMapping("/start/{roomId}/{roomName}")
	public ResponseEntity<?> getFive(
			@PathVariable @ApiParam(value = "방 번호") int roomId,
			@PathVariable @ApiParam(value = "방 제목") String roomName) {
		
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<RoundInfoDto> roundInfoDtoList = doodleService.getFive(roomName);
			resultMap.put("message", SUCCESS);
			data.put("messageType", MessageType.START);
			data.put("roundInfoList", roundInfoDtoList);
			simpMessagingTemplate.convertAndSend("/sub/" + roomId, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("게임 시작(라운드 5개 생성) 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "라운드 결과 저장", notes = "라운드 결과 저장 API", response = Map.class)
	@PostMapping("/round")
	public ResponseEntity<?> saveRoundResult(
			@RequestPart @ApiParam(value = "유저 ID, 라운드 ID") SaveRoundResultDto saveRoundResultDto,
			@RequestPart @ApiParam(value = "그린 이미지") MultipartFile image) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			String imageUrl = doodleService.saveRoundResult(saveRoundResultDto, image);
			resultMap.put("message", SUCCESS);
			resultMap.put("imageUrl", imageUrl);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("라운드 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "라운드 결과 조회", notes = "라운드 결과 조회 API", response = Map.class)
	@GetMapping("/round/{roundId}")
	public ResponseEntity<?> getRoundResult(
			@PathVariable @ApiParam(value = "라운드 ID") int roundId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<GetRoundResultDto> getRoundResultDtoList = doodleService.getRoundResult(roundId);
			resultMap.put("message", SUCCESS);
			resultMap.put("roundResultList", getRoundResultDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("라운드 결과 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "게임 결과 저장", notes = "게임 결과 저장 API", response = Map.class)
	@PostMapping("/game/{userId}/{gameId}/{rank}")
	public ResponseEntity<?> saveRoundResult(
			@PathVariable @ApiParam(value = "유저 ID") String userId,
			@PathVariable @ApiParam(value = "게임 ID") int gameId,
			@PathVariable @ApiParam(value = "순위") int rank) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			doodleService.saveGameResult(userId, gameId, rank);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("게임 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "게임 결과 조회", notes = "게임 결과 조회 API", response = Map.class)
	@GetMapping("/game/{userId}/{gameId}")
	public ResponseEntity<?> getGameResult(
			@PathVariable @ApiParam(value = "유저 ID") String userId,
			@PathVariable @ApiParam(value = "게임 ID") int gameId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			Map<String, Object> rankAndImages = doodleService.getGameResult(userId, gameId);
			resultMap.put("message", SUCCESS);
			resultMap.put("rank", rankAndImages.get("rank"));
			resultMap.put("resultList", rankAndImages.get("resultList"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("게임 결과 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "게임 히스토리 10개 조회", notes = "게임 히스토리 10개 조회 API", response = Map.class)
	@GetMapping("/history/{userId}")
	public ResponseEntity<?> getGameResult(
			@PathVariable @ApiParam(value = "유저 ID") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<GetGameHistoryDto> getGameHistoryDtoList = doodleService.getGameHistory(userId);
			resultMap.put("message", SUCCESS);
			resultMap.put("gameHistoryList", getGameHistoryDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("게임 히스토리 10개 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
