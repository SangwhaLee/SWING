package com.swing.sentency.controller;

import com.swing.five.controller.FiveController;
import com.swing.sentency.model.dto.SentenceDto;
import com.swing.sentency.model.dto.SentencyRankDto;
import com.swing.sentency.model.entity.Sentence;
import com.swing.sentency.model.service.SentencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/sentency")
@Api(tags = {"Sentency 관리 APIs"})
public class SentencyController {
	
	@Autowired
	private SentencyService sentencyService;
	
	public static final Logger logger = LoggerFactory.getLogger(FiveController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@ApiOperation(value = "sentency 사진 업로드", notes = "sentency 사진 업로드 API", response = Map.class)
	@PutMapping("/image")
	public ResponseEntity<?> upload(
			@RequestPart @ApiParam(value = "이미지 정보") MultipartFile image,
			@RequestPart @ApiParam(value = "sentence id") int sentenceId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			String url = sentencyService.upload(image, sentenceId);
			resultMap.put("message", SUCCESS);
			resultMap.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("사진 업로드 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "결과 저장", notes = "게임 결과 저장 API", response = Map.class)
	@PutMapping("/{userId}/{score}")
	public ResponseEntity<?> saveResult(
			@PathVariable @ApiParam(value = "유저 ID", required = true) String userId,
			@PathVariable @ApiParam(value = "점수", required = true) Integer score) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			sentencyService.saveRank(userId, score);
			resultMap.put("message", SUCCESS);
			// 결과 저장 성공한 경우, 성공 메시지 반환, 200 응답 코드
		} catch (Exception e) {
			logger.error("게임 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// 결과 저장 중 에러 발생한 경우 실패 메시지 반환, 500 응답 코드
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "이미지 불러오기(게임 시작)", notes = "게임 이미지 불러오는 API", response = Map.class)
	@GetMapping("")
	public ResponseEntity<?> getSentency() {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			Sentence sentence = sentencyService.getSentency();
			resultMap.put("message", SUCCESS);
			resultMap.put("sentence", SentenceDto.toDto(sentence));
			// 결과 저장 성공한 경우, 성공 메시지 반환, 200 응답 코드
		} catch (Exception e) {
			logger.error("게임 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// 결과 저장 중 에러 발생한 경우 실패 메시지 반환, 500 응답 코드
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "Sentency 랭킹 조회", notes = "Sentency 랭킹 조회 API", response = Map.class)
	@GetMapping("/{userId}")
	public ResponseEntity<?> getRank (
			@PathVariable @ApiParam(value = "Sentency 랭킹 조회할 유저 ID", required = true) String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<SentencyRankDto> sentencyRankDtoList = sentencyService.getRank(userId);
			resultMap.put("sentencyRankList", sentencyRankDtoList);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("Sentency 랭킹 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
}