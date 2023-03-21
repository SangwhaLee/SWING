package com.swing.user.controller;

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
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/user")
@Api(tags = {"회원 관리 API"})
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "사진 업로드 테스트", notes = "사진 업로드 테스트 API", response = Map.class)
	@GetMapping("")
	public ResponseEntity<?> upload(
			@RequestPart @ApiParam(value = "이미지 정보") MultipartFile image) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status;
		
		try {
			String url = userService.upload(image);
			resultMap.put("message", SUCCESS);
			resultMap.put("url", url);
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("사진 업로드 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
