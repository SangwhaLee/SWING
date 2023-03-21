package com.swing.five.controller;

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
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/five")
@Api(tags = {"Hi-five 관리 API"})
public class FiveController {
	
	@Autowired
	private FiveService fiveService;
	
	public static final Logger logger = LoggerFactory.getLogger(FiveController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@PostMapping("/image")
	@ApiOperation(value = "(관리자) 이미지 저장", notes = "게임에 쓰일 이미지 저장 API", response = Map.class)
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
	
	@PostMapping("")
	@ApiOperation(value = "결과 저장", notes = "게임 결과 저장 API", response = Map.class)
	public ResponseEntity<?> saveResult (
			@RequestBody @ApiParam(value = "유저 ID", required = true) String userId,
			@RequestBody @ApiParam(value = "점수", required = true) Integer score) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			fiveService.saveResult(userId, score);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("게임 결과 저장 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
}
