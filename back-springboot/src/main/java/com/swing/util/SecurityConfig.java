	package com.swing.util;

	import com.swing.user.oauth.PrincipalOauthUserService;
	import com.swing.user.oauth.handler.OAuth2FailureHandler;
	import com.swing.user.oauth.handler.OAuth2SuccessHandler;
	import lombok.RequiredArgsConstructor;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.web.cors.CorsConfiguration;
	import org.springframework.web.cors.CorsConfigurationSource;
	import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

	@Configuration
	@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
	@RequiredArgsConstructor
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
	public class SecurityConfig  {

		@Autowired
		private PrincipalOauthUserService principalOauthUserService;
		private final OAuth2SuccessHandler oAuth2SuccessHandler;
		private final OAuth2FailureHandler oAuth2FailureHandler;

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
					.httpBasic().disable() // rest api 이므로 기본설정 사용안함, 기본설정은 비인증시 로그인 폼화면으로 리다이렉트
					.csrf().disable() // rest api만 가능한 jwt인증 기반의 사이트이므로 csrf는 disable처리 합니다.
					.cors().configurationSource(corsConfigurationSource())
					.and()
					.authorizeRequests()
					.antMatchers(PERMIT_URL_ARRAY).permitAll()
					.and()
					.oauth2Login() // OAuth2 로그인 설정 시작 지점
//					.authorizationEndpoint()
//					.baseUri("/oauth2/authorization")
//					.and()
//					.redirectionEndpoint().baseUri("/oauth2/code/**")
//					.and()
					.userInfoEndpoint() //OAuth2 로그인 후 사용자 정보를 가져올 때의 설정 담당
					.userService(principalOauthUserService)
					.and()
					.successHandler(oAuth2SuccessHandler)
					.failureHandler(oAuth2FailureHandler);
//					.redirectionEndpoint().baseUri("/");
			return http.build();
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		// CORS
		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();

			configuration.addAllowedOriginPattern("*");
			configuration.addAllowedMethod("*");
			configuration.addAllowedHeader("*");
			configuration.setAllowCredentials(true);
			configuration.addExposedHeader("Authorization");

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}

		private static final String[] PERMIT_URL_ARRAY = {
				"/**", //다 허용 일단 나중에 수정해야 함
				/* swagger v3 */
				"/v3/api-docs/**",
				"/swagger-ui/**",
				"/oauth2/**"
		};
//		@Bean
//		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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
//			http
//					.csrf().disable().headers().frameOptions().disable() // 2.
//					.and()
//					.authorizeRequests() // 3.
//					.antMatchers("/oauth2").permitAll()
//					.anyRequest().authenticated() // 5.
//					.and()
//					.logout()
//					.logoutSuccessUrl("/") // 6.
//					.and()
//					.oauth2Login() // 7.
//					.userInfoEndpoint() // 8.
//					.userService(principalOauthUserService);
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
//			return http.build();
//
//
//		}
	}
