package com.swing.user.oauth;

import com.swing.five.model.repository.FiveRankRepository;
import com.swing.sentency.model.repository.SentencyRankRepository;
import com.swing.user.model.dto.UserDto;
import com.swing.user.model.entity.User;
import com.swing.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauthUserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FiveRankRepository fiveRankRepository;
	@Autowired
	private SentencyRankRepository sentencyRankRepository;
	
	//구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	//함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		//"registraionId" 로 어떤 OAuth 로 로그인 했는지 확인 가능(google,naver등)
		System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
		System.out.println("getAccessToken: "+userRequest.getAccessToken().getTokenValue());
		System.out.println("getAttributes: "+ super.loadUser(userRequest).getAttributes());
		//구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴(OAuth-Clien라이브러리가 받아줌) -> code를 통해서 AcssToken요청(access토큰 받음)
		// => "userRequest"가 감고 있는 정보
		//회원 프로필을 받아야하는데 여기서 사용되는것이 "loadUser" 함수이다 -> 구글 로 부터 회원 프로필을 받을수 있다.
		
		
		/**
		 *  OAuth 로그인 회원 가입
		 */
		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo =null;
		
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}
		else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"),
					String.valueOf(oAuth2User.getAttributes().get("id")));
		}
		else{
			System.out.println("지원하지 않은 로그인 서비스 입니다.");
		}
		
		String provider = oAuth2UserInfo.getProvider(); //google , kakao
		String providerId = oAuth2UserInfo.getProviderId();
		String userId = provider + providerId;

		
		User userEntity = userRepository.findByUserId(userId);
		//처음 서비스를 이용한 회원일 경우
		if(userEntity == null) {
			userEntity = new User(userId,userId,null,3,1,0,null,null,null,null,null,null,null,null,null,null);

			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(UserDto.toDto(userEntity), oAuth2User.getAttributes());
	}
}