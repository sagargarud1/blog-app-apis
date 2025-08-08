package com.blogappcode.blog.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



//   https://github.com/CodeMythGit/SpringBootTutorial/tree/main/JwtAuthenticationExample/

@Configuration
public class SpringSecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    UserDetailsService userDetailsService() {
    	UserDetails adminUser = User.builder()
    			.username("admin")
    			.password(passwordEncoder().encode("admin"))
    			.roles("ADMIN")
    			.build();
    	
    	UserDetails normalUser = User.builder()
    			.username("user")
    			.password(passwordEncoder().encode("user"))
    			.roles("USER")
    			.build();
    	
    	return new InMemoryUserDetailsManager(adminUser,normalUser);  			//	in Memory Authentication   	    	
    }
    

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
}
