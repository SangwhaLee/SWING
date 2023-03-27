package com.swing.doodle.controller;

import com.swing.doodle.model.dto.CreateRoomDto;
import com.swing.doodle.model.service.DoodleService;
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
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/doodle")
@Api(tags = {"Speedoodle 관리 API"})
public class DoodleController {
	
	@Autowired
	private DoodleService doodleService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
//	private static final String ALREADY_EXIST = "already exists";
	
	@ApiOperation(value = "방 만들기", notes = "방 만들기 API", response = Map.class)
	@PostMapping("")
	public ResponseEntity<?> createRoom(
			@RequestBody @ApiParam(value = "방 정보") CreateRoomDto createRoomDto) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int roomNumber = doodleService.createRoom(createRoomDto);
			resultMap.put("message", SUCCESS);
			resultMap.put("roomNumber", roomNumber);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("방 만들기 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
}
