package com.tokioshool.filmotokio.config;

import com.tokioshool.filmotokio.security.FilmUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.security.Constant;
import com.tokioshool.filmotokio.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private FilmUserDetailsService usersDetailsService;
	@Autowired 
	private UserService userService;
	
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(usersDetailsService).passwordEncoder(passwordEncoder());
	}
	
	protected void configure(HttpSecurity http)throws Exception{
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/registration").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/new-user").permitAll()
			.antMatchers("/films/**").permitAll()
			.antMatchers("/admin/**").hasAuthority(Constant.ADMIN_ROLE)
			.anyRequest().authenticated().and()
			.formLogin()
			.loginPage(Constant.LOGIN_URL)
			.defaultSuccessUrl(Constant.LOGIN_SUCCESS_URL)
			.failureUrl(Constant.LOGIN_FAILURE_URL)
			.and()
			.logout()
			.addLogoutHandler((request, response, authentication) -> {
	               User activeUser = userService.findByActive(true);
	               userService.modifyActive(activeUser.getId());
	            })
			.logoutRequestMatcher(new AntPathRequestMatcher(Constant.LOGOUT_URL))
			.logoutSuccessUrl(Constant.LOGOUT_SUCCESS_URL);
	}
	
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/templates/**", "/css/**", "/js/**", "/images/**",
				"/fonts/**", "/error/**", "/upload/**", "/webjars/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
