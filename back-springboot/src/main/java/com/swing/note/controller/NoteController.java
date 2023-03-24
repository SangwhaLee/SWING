package com.swing.note.controller;

import com.swing.note.model.dto.GetWordNoteDto;
import com.swing.note.model.service.NoteService;
import com.swing.user.controller.UserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/note")
@Api(tags = {"오답노트 관리 APIs"})
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@ApiOperation(value = "틀린 단어 저장", notes = "틀린 단어 저장 API", response = Map.class)
	@PostMapping("/word/{userId}/{wordId}")
	public ResponseEntity<?> saveWord (
			@PathVariable @ApiParam(value = "유저 ID", required = true) String userId,
			@PathVariable @ApiParam(value = "단어 등록 번호", required = true) int wordId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			if (noteService.saveWord(userId, wordId)) resultMap.put("message", SUCCESS);
			else resultMap.put("message", ALREADY_EXIST);
		} catch (Exception e) {
			logger.error("틀린 단어 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "틀린 단어 조회", notes = "틀린 단어 조회 API", response = Map.class)
	@GetMapping("/word/{userId}/{key}")
	public ResponseEntity<?> getWords (
			@PathVariable @ApiParam(value = "유저 ID", required = true) String userId,
			@PathVariable @ApiParam(value = "0(전체 조회), 1(랜덤 5개 조회)", required = true) int key) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<GetWordNoteDto> getWordNoteDtoList = noteService.getWords(userId, key);
			resultMap.put("wordNoteList", getWordNoteDtoList);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 단어 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "틀린 단어 체크", notes = "틀린 단어 체크 API", response = Map.class)
	@PutMapping("/word/{wordNoteId}")
	public ResponseEntity<?> checkWord (
			@PathVariable @ApiParam(value = "오답노트 등록 번호", required = true) int wordNoteId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			noteService.checkWord(wordNoteId);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 단어 체크 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
