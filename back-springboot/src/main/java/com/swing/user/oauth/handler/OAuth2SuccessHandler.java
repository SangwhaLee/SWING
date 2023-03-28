package com.swing.user.oauth.handler;

import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import com.swing.user.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
			user.setRefreshToken(refreshToken);
			userRepository.save(user);
			return "http://localhost:3000?refreshToken="+refreshToken+"&accessToken="+accessToken;
//			return UriComponentsBuilder.fromUriString("/oauth")
//					.queryParam("access-token", accessToken)
//					.queryParam("refresh-token",refreshToken)
//					.queryParam("user", UserDto.toDto(user))
//					.build()
//					.encode()
//					.toUriString();
		}
		// user를 찾을 수 없으면 login으로 튕기게 처리
		return "http://localhost:3000/login";

    }
	
}
