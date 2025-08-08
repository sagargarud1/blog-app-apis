package com.blogappcode.blog.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
	
	@Autowired
	private UserDetailsService detailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper helper;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
			
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		UserDetails userDetails  = this.detailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.helper.generateToken(userDetails);
		
//		JwtResponse response = JwtResponse.builder()
//				.jwtToken(token)
//				.username(userDetails.getUsername()).build();
		JwtResponse response = new JwtResponse();
		response.setJwtToken(token);
		response.setUsername(userDetails.getUsername());
		
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
	}
	
	
	
	private void doAuthenticate(String username,String password) {
		UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(username, password);
		
 		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("credential invalid !!");
		}
	}
	
}



