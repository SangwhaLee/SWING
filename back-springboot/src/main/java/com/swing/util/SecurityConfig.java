	package com.swing.util;

	import com.swing.user.model.service.JwtService;
	import com.swing.user.oauth.JwtFilter;
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
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
		@Autowired
		private JwtService jwtService;
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
					.anyRequest().authenticated()
					//JwtSecurityConfig 적용
					.and()
					.apply(new JwtSecurityConfig(jwtService))
					.and()
					.addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
					.oauth2Login() // OAuth2 로그인 설정 시작 지점
					.userInfoEndpoint() //OAuth2 로그인 후 사용자 정보를 가져올 때의 설정 담당
					.userService(principalOauthUserService)
					.and()
					.successHandler(oAuth2SuccessHandler)
					.failureHandler(oAuth2FailureHandler);
			http
					.logout()
					.logoutSuccessUrl("http://localhost:3000");
			
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
			configuration.addExposedHeader("Access-Token");

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}

		private static final String[] PERMIT_URL_ARRAY = {
				//"/**", //다 허용 일단 나중에 수정해야 함
				/* swagger v3 */
				"/v3/api-docs/**",
				//"/swagger-ui/**",
				"/oauth2/**"
		};

	}
