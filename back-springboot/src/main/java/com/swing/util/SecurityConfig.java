	package com.swing.util;

	import com.swing.user.oauth.PrincipalOauthUserService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.web.SecurityFilterChain;

	@Configuration
	@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
	public class SecurityConfig  {

		@Autowired
		private PrincipalOauthUserService principalOauthUserService;

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//			http.csrf().disable()
//					.authorizeRequests()
////					.antMatchers("/user/**").authenticated()
//					.anyRequest().authenticated()
//
//					.and()
//					.formLogin()
//					.loginPage("/login") //미인증자일경우 해당 uri를 호출
//					.loginProcessingUrl("/login") //login 주소가 호출되면 시큐리티가 낚아 채서(post로 오는것) 대신 로그인 진행 -> 컨트롤러를 안만들어도 된다.
//					.defaultSuccessUrl("/")
//
//					.and()
//					.oauth2Login()
//					.loginPage("/login")
//					.defaultSuccessUrl("/")
//					.userInfoEndpoint()
//					.userService(principalOauthUserService);//구글 로그인이 완료된(구글회원) 뒤의 후처리가 필요함 . Tip.코드x, (엑세스 토큰+사용자 프로필 정보를 받아옴)
//
//
//			return http.build();
			http
					.csrf().disable().headers().frameOptions().disable() // 2.
					.and()
					.authorizeRequests() // 3.
					.antMatchers("/oauth2").permitAll()
					.anyRequest().authenticated() // 5.
					.and()
					.logout()
					.logoutSuccessUrl("/") // 6.
					.and()
					.oauth2Login() // 7.
					.userInfoEndpoint() // 8.
					.userService(principalOauthUserService);
//			http.csrf().disable()
//					.authorizeRequests()
//					.antMatchers("/oauth2").authenticated()
//					.anyRequest().permitAll()
////
//					.and()
//					.oauth2Login()
//					.loginPage("	/login")
//					.defaultSuccessUrl("/")
//					.userInfoEndpoint()
//					.userService(principalOauthUserService);//구글 로그인이 완료된(구글회원) 뒤의 후처리가 필요함 . Tip.코드x, (엑세스 토큰+사용자 프로필 정보를 받아옴)
			return http.build();


		}
	}
