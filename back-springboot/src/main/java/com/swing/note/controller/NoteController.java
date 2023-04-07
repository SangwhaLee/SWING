package com.swing.note.controller;

import com.swing.note.model.dto.GetSentenceNoteDto;
import com.swing.note.model.dto.GetWordNoteDto;
import com.swing.note.model.service.NoteService;
import com.swing.user.model.service.UserService;
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
	@Autowired
	private UserService userService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	/*
	 *******************
	 ** APIs for Word **
	 *******************
	 */
	
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
			if(key==1){
				int coupon = userService.getCouponCnt(userId);
				resultMap.put("coupon",coupon);
			}
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
	
	@ApiOperation(value = "틀린 단어 삭제", notes = "틀린 단어 삭제 API", response = Map.class)
	@DeleteMapping("/word/{wordNoteId}")
	public ResponseEntity<?> deleteWord (
			@PathVariable @ApiParam(value = "오답노트 등록 번호", required = true) int wordNoteId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			noteService.deleteWord(wordNoteId);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 단어 삭제 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	/*
	 ***********************
	 ** APIs for Sentence **
	 ***********************
	 */
	
	@ApiOperation(value = "틀린 문장 저장", notes = "틀린 문장 저장 API", response = Map.class)
	@PostMapping("/sentence/{userId}/{sentenceId}")
	public ResponseEntity<?> saveSentence (
			@PathVariable @ApiParam(value = "유저 ID", required = true) String userId,
			@PathVariable @ApiParam(value = "문장 등록 번호", required = true) int sentenceId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			if (noteService.saveSentence(userId, sentenceId)) resultMap.put("message", SUCCESS);
			else resultMap.put("message", ALREADY_EXIST);
		} catch (Exception e) {
			logger.error("틀린 문장 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "틀린 문장 조회", notes = "틀린 문장 조회 API", response = Map.class)
	@GetMapping("/sentence/{userId}/{key}")
	public ResponseEntity<?> getSentences (
			@PathVariable @ApiParam(value = "유저 ID", required = true) String userId,
			@PathVariable @ApiParam(value = "0(전체 조회), 1(랜덤 1개 조회)", required = true) int key) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<GetSentenceNoteDto> getSentenceNoteDtoList = noteService.getSentences(userId, key);
			resultMap.put("sentenceNoteList", getSentenceNoteDtoList);
			if(key==1){
				int coupon = userService.getCouponCnt(userId);
				resultMap.put("coupon",coupon);
			}
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 문장 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "틀린 문장 체크", notes = "틀린 문장 체크 API", response = Map.class)
	@PutMapping("/sentence/{sentenceNoteId}")
	public ResponseEntity<?> checkSentence (
			@PathVariable @ApiParam(value = "오답노트 등록 번호", required = true) int sentenceNoteId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			noteService.checkSentence(sentenceNoteId);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 문장 체크 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "틀린 문장 삭제", notes = "틀린 문장 삭제 API", response = Map.class)
	@DeleteMapping("/sentence/{sentenceNoteId}")
	public ResponseEntity<?> deleteSentence (
			@PathVariable @ApiParam(value = "오답노트 등록 번호", required = true) int sentenceNoteId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			noteService.deleteSentence(sentenceNoteId);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("틀린 문장 삭제 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
