package com.michalkolos.account.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@Configuration
@EnableWebSecurity(debug=false)
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final UserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfigurerImpl(RestAuthenticationEntryPoint restAuthenticationEntryPoint,
	                                 UserDetailsService userDetailsService) {

		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.userDetailsService = userDetailsService;
	}




	public void configure(HttpSecurity http) throws Exception {

		http.httpBasic()
				.authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
				.and()
				.csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
				.and()
				.authorizeRequests() // manage access
				.antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
				.antMatchers(HttpMethod.GET, "/api/empl/payment/").hasAnyRole("USER")
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
				.userDetailsService(userDetailsService) // user store 1
				.passwordEncoder(getEncoder());
	}


	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(getEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

}
