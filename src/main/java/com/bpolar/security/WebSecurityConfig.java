package com.bpolar.security;


import javax.sql.DataSource;

import com.bpolar.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource datasource;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
				authorizeRequests()
				.antMatchers("/u/login", "/u/register", "/u/create-account").permitAll()
				.antMatchers("/a/login", "/a/register", "/a/create-account").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/u/*", "/u/*/*", "/u/*/*/*").hasAnyAuthority( "User")
				.antMatchers("/a/*").hasAnyAuthority("Admin")
				.anyRequest().permitAll()
			.and()
			.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler(successHandler)
				.permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll();
			
	}
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
}
