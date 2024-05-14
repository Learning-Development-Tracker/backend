package com.lps.ldtracker.configuration;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.lps.ldtracker.permission.Permission.*;
import static com.lps.ldtracker.security.RoleSecurity.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilterConfiguration jwtAuthenticationFilterConfiguration;
	private final AuthenticationProvider authenticationProvider;
	
	private static final String[] WHITE_LIST_URL = {
		"/v1/api-docs",
		"/api/health",
		"/api/v1/authentication/**",
		"/api/v1/forgot-password/**",
		"api/v1/admin/**",
		"/api/v1/resources/**"
//		"/h2-console/**"
    };
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.headers(httpSecurityHeadersConfigurer -> {
			    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
			    httpSecurityHeadersConfigurer.frameOptions().sameOrigin();
			}) 
			.authorizeHttpRequests(request -> request
				.requestMatchers(WHITE_LIST_URL)
				.permitAll()
				.requestMatchers("/api/v1/admin/**")
				.hasAnyRole(ADMIN.name(), USER.name())
				.requestMatchers(GET, "/api/v1/admin/**")
				.hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                .requestMatchers(POST, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                .requestMatchers(PUT, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                .requestMatchers("/api/v1/**")
				.hasAnyRole(GUEST.name())
                .anyRequest()
                .authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilterConfiguration, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}