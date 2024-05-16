package com.lps.ldtracker.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.repository.UserDtlRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ApplicationConfiguration {
	
	private final UserDtlRepository userDtlRepository;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userDtlRepository.findByUserName(username)
			.orElseThrow(() -> new UsernameNotFoundException(LdTrackerConstants.USER_DOES_NOT_EXISTS));
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
    
    @ConfigurationProperties("app.datasource")
    public HikariDataSource dataSource() throws SQLException {
    	HikariConfig config = new HikariConfig();
    	 // Connection pool properties 
        config.setConnectionTimeout(20000); // 20 seconds in milliseconds 
        config.setMaximumPoolSize(10);  // Maximum number of connections in the pool 
        config.setMinimumIdle(5);        // Minimum number of idle connections in the pool 
        config.setIdleTimeout(60000);      // Maximum time a connection can remain idle in the pool (60 seconds) 
        config.setMaxLifetime(1800000);     // Maximum lifetime of a connection in the pool (30 minutes) 
        config.setKeepaliveTime(600000);    // Keepalive time in milliseconds (10 minutes) 
        config.setConnectionTestQuery("SELECT 1"); // Connection test query 
        config.setPoolName("MyPool");       // Pool name 
        config.setIdleTimeout(300000); // 5 minutes 
  
        HikariDataSource dataSource = new HikariDataSource(config); 
  
        // Creating connection with the pool 
        try (Connection connection = dataSource.getConnection())  
        { 
            System.out.println("Connected to database!"); 
  
        } catch (SQLException e) { 
            // In case of failed Connection 
            e.printStackTrace(); 
        } finally { 
            // Close the DataSource when done 
            dataSource.close(); 
        } 
    	return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

}