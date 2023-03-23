package com.swing.note.controller;

import com.swing.note.model.dto.WordNoteDto;
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
	
	@ApiOperation(value = "틀린 단어 전체 조회", notes = "틀린 단어 전체 조회 API", response = Map.class)
	@GetMapping("/word")
	public ResponseEntity<?> getAll (
			@RequestBody @ApiParam(value = "유저 ID", required = true) String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<WordNoteDto> wordNoteDtoList = noteService.getAll(userId);
			resultMap.put("wordNoteList", wordNoteDtoList);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("이미지 5개 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
}
