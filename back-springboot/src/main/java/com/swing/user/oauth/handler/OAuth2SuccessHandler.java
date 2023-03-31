package com.swing.user.oauth.handler;

import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import com.swing.user.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	private final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
        String targetUrl = getTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response를 이미 받으셨습니다. 다음URL에 리다이렉트 할 수 없습니다. URL: " + targetUrl);
            return;
        }
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	protected String getTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		User user = userRepository.findByUserId(authentication.getName());
		if (user != null) {
			String accessToken = jwtService.createAccessToken(user.getUserId());
			String refreshToken = jwtService.createRefreshToken(user.getUserId());
			user.setProfileImageUrl("https://a405-swing.s3.ap-northeast-2.amazonaws.com/images/profile/default.png");
			user.setRefreshToken(refreshToken);
			userRepository.save(user);
			Authentication accessAuth = jwtService.getAuthentication(accessToken);
			Authentication refreshAuth = jwtService.getAuthentication(refreshToken);
			SecurityContextHolder.getContext().setAuthentication(accessAuth);
			//System.out.printf("Security Context에 '%s' 인증 정보를 저장했습니다\n", accessAuth.getName());
			
			SecurityContextHolder.getContext().setAuthentication(refreshAuth);
			//System.out.printf("Security Context에 '%s' 인증 정보를 저장했습니다\n", refreshAuth.getName());
			
			return UriComponentsBuilder.fromHttpUrl("http://j8a405.p.ssafy.io:3000")
					.queryParam("access-token", accessToken)
					.queryParam("refresh-token",refreshToken)
					.queryParam("user", UserDto.toDto(user))
					.build()
					.encode()
					.toUriString();
		}
		// user를 찾을 수 없으면 login으로 튕기게 처리
		return UriComponentsBuilder.fromHttpUrl("http://j8a405.p.ssafy.io:3000")
				.path("/login")
				.build()
				.encode()
				.toUriString();

    }
	
}
