package com.swing.five.controller;

import com.swing.five.model.dto.FiveRankDto;
import com.swing.five.model.dto.FiveResultDto;
import com.swing.five.model.dto.FiveStatDto;
import com.swing.five.model.dto.WordDto;
import com.swing.five.model.service.FiveService;
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
@RequestMapping("/five")
@Api(tags = {"Hi-five 관리 APIs"})
public class FiveController {
	
	@Autowired
	private FiveService fiveService;
	
	public static final Logger logger = LoggerFactory.getLogger(FiveController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@ApiOperation(value = "(관리자) 이미지 저장", notes = "게임에 쓰일 이미지 저장 API", response = Map.class)
	@PostMapping("/image")
	public ResponseEntity<?> image (
			@RequestBody @ApiParam(value = "이미지", required = true) MultipartFile multipartFile,
			@RequestBody @ApiParam(value = "내용", required = true) String content,
			@RequestBody @ApiParam(value = "한국어 뜻", required = true) String meaningKr,
			@RequestBody @ApiParam(value = "영어 뜻", required = true) String meaningEn) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			// 저장돼있지 않은 단어면 SUCCESS 반환
			if (fiveService.image(multipartFile, content, meaningKr, meaningEn) != null) resultMap.put("message", SUCCESS);
			// 이미 저장돼있는 단어면 ALREADY_EXIST 반환
			else resultMap.put("message", ALREADY_EXIST);
		} catch (Exception e) {
			logger.error("(관리자) 이미지 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "이미지 5개 조회", notes = "이미지 5개 조회 API", response = Map.class)
	@GetMapping("")
	public ResponseEntity<?> getFive () {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<WordDto> wordDtoList = fiveService.getFive();
			resultMap.put("wordList", wordDtoList);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("이미지 5개 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "결과 저장", notes = "게임 결과 저장 API", response = Map.class)
	@PutMapping("")
	public ResponseEntity<?> saveResult (
			@RequestBody @ApiParam(value = "오늘 결과 정보", required = true) FiveResultDto fiveResultDto) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			fiveService.saveRank(fiveResultDto.getUserId(), fiveResultDto.getDayScore());
			fiveService.saveResult(fiveResultDto);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("게임 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "Hi-five 랭킹, 스탯 조회", notes = "Hi-five 랭킹, 스탯 조회 API", response = Map.class)
	@GetMapping("/{userId}")
	public ResponseEntity<?> getRankAndStat (
			@PathVariable @ApiParam(value = "Hi-five 랭킹, 스탯 조회할 유저 ID", required = true) String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<FiveRankDto> fiveRankDtoList = fiveService.getRank(userId);
			FiveStatDto fiveStatDto = fiveService.getStat(userId);
			resultMap.put("fiveRankList", fiveRankDtoList);
			resultMap.put("fiveStat", fiveStatDto);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("Hi-five 랭킹 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
}
