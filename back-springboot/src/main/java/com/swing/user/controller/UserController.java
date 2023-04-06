package com.swing.user.controller;

import com.swing.user.model.dto.ModifyDto;
import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.service.JwtService;
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
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/user")
@Api(tags = {"회원 관리 API"})
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";

	@ApiOperation(value = "소셜로그인", notes = "access-token, Refresh-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/socialLogin")
	public ResponseEntity<?> socialLogin(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보.", required = true) UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();

		HttpStatus status = HttpStatus.OK;
		try {
			// access token 및 refresh token 발급
			String accessToken = jwtService.createAccessToken("userid", userDto.getUserId());// key, data
			String refreshToken = jwtService.createRefreshToken("userid", userDto.getUserId());// key, data
			
			// 로그인
			UserDto user = userService.socialLogin(userDto, refreshToken);

			if(user!=null) {
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("profileImageUrl", user.getProfileImageUrl());
				resultMap.put("userId", user.getUserId());
				resultMap.put("nickname", user.getNickname());
				resultMap.put("coupon",user.getCoupon());
				resultMap.put("message", SUCCESS);
			}
			else {
				status = HttpStatus.ACCEPTED;
				resultMap.put("message",ALREADY_EXIST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "로그아웃", notes = "로그아웃하는 유저의 Refresh Token을 삭제한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> logoutUser(
			@PathVariable @ApiParam(value = "로그아웃 할 유저의 ID", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			userService.delRefreshToken(userId);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "회원정보 조회", notes = "회원정보 조회 API", response = Map.class)
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserInfo(
			@PathVariable @ApiParam(value = "회원 정보") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			UserDto userDto = userService.getUserInfo(userId);
			if(userDto != null){
				resultMap.put("message", SUCCESS);
				resultMap.put("user", userDto);
			}
			else{  // 잘못된 유저의 정보를 요청한 경우
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("회원정보 조회 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "닉네임 중복 확인", notes = "닉네임 중복 확인 API", response = Map.class)
	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<?> checkDuplicate(
			@PathVariable @ApiParam(value = "중복확인할 닉네임") String nickname) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			if(userService.checkDuplicate(nickname)){  // 중복 x
				resultMap.put("possible",true);
			}
			else{  // 중복 o
				resultMap.put("possible",false);
			}
			resultMap.put("message", SUCCESS);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("닉네임 중복 확인 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정 API", response = Map.class)
	@PutMapping("")
	@Transactional
	public ResponseEntity<?> setUserInfo(
			@RequestPart @ApiParam(value = "회원 정보", required = true) ModifyDto modifyDto,
			@RequestPart @ApiParam(value = "프로필이미지") MultipartFile image
			) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			UserDto user = userService.setUserInfo(modifyDto, image);
			resultMap.put("user",user);
			resultMap.put("message", SUCCESS);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("회원 정보 수정 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}
	
	@ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴 API", response = Map.class)
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(
			@PathVariable @ApiParam(value = "회원 아이디") String userId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			if(userService.deleteUser(userId)){
				resultMap.put("message", SUCCESS);
			}
			else{
				resultMap.put("message",FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("회원 탈퇴 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
		
	}

	@ApiOperation(value = "access-token 유효성 검사", notes = "access-token 유효성 검사.", response = Map.class)
	@PostMapping("/check")
	public ResponseEntity<?> checkToken(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		// Header에서 access token 가져오기
		String token = request.getHeader("Access-Token");
		
		// 액세스토큰이 유효한지 확인한 후 결과 반환.
		if (jwtService.checkToken(token)) {
			resultMap.put("message", SUCCESS);
		} else {
			logger.debug("access-token 만료.");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "access-token 재발급", notes = "만료된 access-token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		// Header에서 refresh token 가져오기
		String token = request.getHeader("Refresh-Token");
		logger.debug("token : {}, userId : {}", token, userDto.getUserId());
		
		// RefreshToken을 받으면 이 토큰이 유효한지 확인한 후 AccessToken을 재발급한다.
		if (jwtService.checkToken(token)) {
			String refreshToken = userService.getRefreshToken(userDto.getUserId());
			if (refreshToken == null) {
				// 잘못된 유저정보로 토큰 요청한 경우
				resultMap.put("message", FAIL);
				return new ResponseEntity<>(resultMap, status);
			}
			if (token.equals(refreshToken)) {
				// access token 갱신
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				logger.debug("access-token : {}", accessToken);
				logger.debug("access-token 재발급 완료.");
				resultMap.put("access-token", accessToken);
			}
		} else {
			logger.debug("refresh-token 만료.");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
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
			User user = userService.getSentencyCnt(userId);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("sentencyCnt", user.getSentencyCnt());
			resultMap.put("coupon", user.getCoupon());
			
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
	
	@ApiOperation(value = "sentency 쿠폰 개수 수정", notes = "sentency 쿠폰 개수 수정 API", response = Map.class)
	@PutMapping("/coupon/{userId}/{couponCnt}")
	public ResponseEntity<?> setCouponCnt(
			@PathVariable @ApiParam(value = "유저 아이디") String userId,
			@PathVariable @ApiParam(value = "수정 값") int couponCnt) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			userService.setCouponCnt(userId,couponCnt);
			resultMap.put("message", SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("쿠폰 개수 수정 실패 : {}", e);
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
}
