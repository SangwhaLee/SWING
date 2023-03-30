package com.swing.user.oauth;

import com.swing.user.model.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	public static final String AUTHORIZATION_HEADER = "Access-Token";
	private JwtService jwtService;
	public JwtFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	//실제 필터링 로직, 토근의 인증정보를 SecurityContext에 저장하기 위한 역할 수행
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String jwt = resolveToken(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();

		if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
			Authentication authentication = jwtService.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
//			System.out.printf("--Security Context에 '%s' 인증 정보를 저장했습니다, uri: %s", authentication.getName(), requestURI);
			logger.debug("--Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
		} else {
//			System.out.printf("유효한 JWT 토큰이 없습니다, uri: %s", requestURI);
			logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	//Request Header에서 토큰 정보를 꺼내오는 로직
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		//System.out.println("Token = "+bearerToken+"\n");
		if (StringUtils.hasText(bearerToken)) {
			return bearerToken;
		}
		
		return null;
	}
}