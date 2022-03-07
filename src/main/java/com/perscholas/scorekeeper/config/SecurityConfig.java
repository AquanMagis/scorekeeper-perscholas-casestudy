package com.perscholas.scorekeeper.config;

import com.perscholas.scorekeeper.security.PlayerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	PlayerDetailService playerDetailService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception{
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/pub/**", "/error/**", "/login", "/login/**", "/register", "/register/**").permitAll()
				.antMatchers("**").authenticated()
				.and()


				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login/login-submit")
				.and()

				.logout()
				.invalidateHttpSession(true)
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and()

				.exceptionHandling()
				.accessDeniedPage("/error/404")
				;
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(playerDetailService);
		auth.authenticationProvider(getAuthenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
