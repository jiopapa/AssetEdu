package kr.co.kfs.assetedu.servlet.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig {

	@Bean
    public UserDetailsService userDetailsService() {
		return new SecurityUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean	@Order(1)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();
		//동일한 url로 접속했는지 체크
		http.cors(); 
		http.authorizeHttpRequests(
				(auth)-> auth
				//모두 들어오게
				.antMatchers("/", "/login").permitAll() 
				//admin/url은 admin만 접속
				.antMatchers("/admin/**").hasRole("ADMIN") 
				// test도 admin 만 접속
				.antMatchers("/test/**").hasRole("ADMIN") 
				.anyRequest().authenticated()
		);
	//  login은 로그인폼 띄워라
		http.formLogin().loginPage("/login") 
			.defaultSuccessUrl("/main", true);
		//로그인버튼누르면 실행되는 프로세스
		http.formLogin().loginProcessingUrl("/loginProcess")  
			.defaultSuccessUrl("/main", true)
			//실패하면 다시 로그인
			.failureUrl("/login?error=true")
			.successHandler(new AuthenticationSuccessHandler() {
				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//					System.out.println("loginId:" + userPrincipal.getUserId());
//					System.out.println("loginNm:" + userPrincipal.getUsername());
					//로그인 성공하면 세션에 id와 이름을 넣어라
					request.getSession().setAttribute("userId", userPrincipal.getUserId()); 
					request.getSession().setAttribute("userNm", userPrincipal.getUsername());
					response.sendRedirect("/main");
				}
			} );
		
		//최대 세션갯수 1개, 새로운 로그인 차단
		http.sessionManagement()
			.maximumSessions(1)
			.maxSessionsPreventsLogin(true)
			.expiredUrl("/login?error")            
		    .sessionRegistry(sessionRegistry());
		//동시 접속 허용
//		http.sessionManagement()
//			.maximumSessions(-1);

		http.logout().logoutUrl("/logout")
			.addLogoutHandler(new LogoutHandler() {
				
				@Override
				public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
					log.debug("---------------------------> loggout");
					request.getSession().invalidate();
				}
			})
			.logoutSuccessUrl("/login")			
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.permitAll();
		
		http.exceptionHandling().accessDeniedPage("/error.jsp");
		http.userDetailsService(userDetailsService());
		return http.build();
		
	}

	@Bean
	@Order(0)
	public SecurityFilterChain exceptionSecurityFilterChain(HttpSecurity http) throws Exception {
		http.requestMatchers((matchers) -> matchers.antMatchers("/static/**"))
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll()).requestCache().disable()
				.securityContext().disable().sessionManagement().disable();

		return http.build();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

}
