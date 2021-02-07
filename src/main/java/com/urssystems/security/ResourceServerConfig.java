package com.urssystems.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/user/**").permitAll()
				.antMatchers("/zonalmpr/**").hasAnyAuthority("ROLE_ZONALMPR")
				.antMatchers("/vendor/**").hasAnyAuthority("ROLE_VENDOR")
				.antMatchers("/security/**").hasAnyAuthority("ROLE_SECURITY")
				.and().exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
