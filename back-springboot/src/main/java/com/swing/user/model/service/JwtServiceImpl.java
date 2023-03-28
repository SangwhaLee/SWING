package com.swing.user.model.service;

import com.swing.user.exception.TokenValidFailedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
	private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1; // day
	private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 7; // day

	@Value("${jwt.secret}")
	private String secretSalt;

	private Key key;

	@PostConstruct
	protected void init() {
		key = Keys.hmacShaKeyFor(secretSalt.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public <T> String createAccessToken(String userId) {
		return create(userId, "swing", 1000 * 60 * 60 * 24 * ACCESS_TOKEN_EXPIRE_MINUTES);
	}

	@Override
	public <T> String createAccessToken(String userId, String role) {
		return create(userId, "swing", 1000 * 10 * 60 * 24 * ACCESS_TOKEN_EXPIRE_MINUTES, role);
	}

	@Override
	public <T> String createRefreshToken(String userId) {
		return create(userId, "swing", 1000 * 60 * 60 * 24 * REFRESH_TOKEN_EXPIRE_MINUTES);
	}

	@Override
	public <T> String createRefreshToken(String userId, String role) {
		return create(userId, "swing", 1000 * 60 * 60 * 24 * REFRESH_TOKEN_EXPIRE_MINUTES,role);
	}

	@Override
	public <T> String create(String userId, String subject, int expir) {
		String jwt = Jwts.builder()
				.setHeaderParam("type", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + expir))
				.setSubject(subject)
				.claim("userId", userId)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		return jwt;
	}

	@Override
	public <T> String create(String userId, String subject, int expir, String role) {
		String jwt = Jwts.builder()
				.setHeaderParam("type", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + expir))
				.setSubject(subject).claim("userId", userId)
				.claim("role", role)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		return jwt;
	}

	// 유효기간이 다되어가면 refresh token검사해서 갱신해줘야 함!!
	@Override
	public void validateToken(String jwtToken) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
			logger.debug("claims: {}", claims);
		} catch (SignatureException | MalformedJwtException e) {
			logger.error("SignatureException : ", e.getMessage());
			throw new TokenValidFailedException("사용할 수 없는 Signature입니다.");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token : ", e.getMessage());
			throw new TokenValidFailedException("사용 기간이 만료된 토큰입니다.");
		} catch (Exception e) {
			logger.error("Unexpected error : ", e.getMessage());
			throw new TokenValidFailedException("토큰 검증 중 예상치 못한 오류가 발생했습니다.");
		}
	}

	@Override
	public Map<String, Object> get(String jwtToken) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
		} catch (Exception e) {
			logger.error("jwt parseClaims error : ", e.getMessage());
			throw new TokenValidFailedException("토큰 해석 중 예상치 못한 오류가 발생했습니다.");
		}
		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);
		return value;
	}

	@Override
	public long getUserId(String jwtToken) {
		return Long.parseLong(String.valueOf(this.get(jwtToken).get("userId")));
	}

	public Authentication getAuthentication(String token) {
		validateToken(token);
		Map<String, Object> claims = get(token);
		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(new String[] { claims.get("role").toString() }).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		logger.debug("claims subject := [{}]", claims.get("subject"));
		User principal = new User((String) claims.get("subject"), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

}
