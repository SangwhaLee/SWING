package com.swing.user.controller;

import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.service.UserService;
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
@RequestMapping("/user")
@Api(tags = {"회원 관리 API"})
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@ApiOperation(value = "소셜 로그인", notes = "소셜 로그인 API", response = Map.class)
	@PostMapping("")
	public ResponseEntity<?> login(
			@RequestPart @ApiParam(value = "유저 정보") User user) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			UserDto userDto = userService.login(user);
			resultMap.put("message", SUCCESS);
			resultMap.put("user", userDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("사진 업로드 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "사진 업로드 테스트", notes = "사진 업로드 테스트 API", response = Map.class)
	@GetMapping("")
	public ResponseEntity<?> upload(
			@RequestPart @ApiParam(value = "이미지 정보") MultipartFile image) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			String url = userService.upload(image);
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
	
	@ApiOperation(value = "sentency 1일 도전횟수 조회", notes = "sentency 도전횟수 조회 API", response = Map.class)
	@GetMapping("/sentency/{userId}")
	public ResponseEntity<?> getSentencyCnt(
			@PathVariable @ApiParam(value = "유저 아이디") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int sentencyCnt = userService.getSentencyCnt(userId);
			resultMap.put("message", SUCCESS);
			resultMap.put("sentencyCnt", sentencyCnt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sentency 일일 도전횟수 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "sentency 1일 도전횟수 수정", notes = "sentency 도전횟수 수정 API", response = Map.class)
	@PutMapping("/sentency/{userId}/{sentencyCnt}")
	public ResponseEntity<?> setSentencyCnt(
			@PathVariable @ApiParam(value = "유저 아이디") String userId,
			@PathVariable @ApiParam(value = "수정 값") int sentencyCnt) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			userService.setSentencyCnt(userId,sentencyCnt);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sentency 일일 도전횟수 수정 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "Hi-five 1일 도전횟수 조회", notes = "Hi-five 도전횟수 조회 API", response = Map.class)
	@GetMapping("/five/{userId}")
	public ResponseEntity<?> getFiveCnt(
			@PathVariable @ApiParam(value = "유저 아이디") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int fiveCnt = userService.getFiveCnt(userId);
			resultMap.put("message", SUCCESS);
			resultMap.put("fiveCnt", fiveCnt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Hi-five 일일 도전횟수 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "Hi-five 1일 도전횟수 수정", notes = "Hi-five 도전횟수 수정 API", response = Map.class)
	@PutMapping("/five/{userId}/{fiveCnt}")
	public ResponseEntity<?> setFiveCnt(
			@PathVariable @ApiParam(value = "유저 아이디") String userId,
			@PathVariable @ApiParam(value = "수정 값") int fiveCnt) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			userService.setFiveCnt(userId,fiveCnt);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Hi-five 일일 도전횟수 수정 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
