package com.blogappcode.blog.config.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("Authorization");
		log.info("Header : {}",requestHeader);
		
		String userName=null;
		String token = null;
		
		if(requestHeader!=null &&requestHeader.startsWith("Bearer")) {
			
			token = requestHeader.substring(7);
			
			try {
				userName = this.jwtHelper.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				log.info("illegaal argument while fetching the username. ");
				e.printStackTrace();
			}catch (ExpiredJwtException e) {
				log.info("given jwt token is expired");
				e.printStackTrace();
			}catch(MalformedJwtException e) {
				log.info("some changes found in jwt token, invalid token!");
				e.printStackTrace();
			}catch(Exception e) {
				log.info("error occured in jwt token.");
				e.printStackTrace();
			}
		}else {
			log.info("invalid Header Value .");
		}
		
		if(userName!=null &&SecurityContextHolder.getContext().getAuthentication()==null) //check security context null or not initially it is null
		{
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			
			if(validateToken) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken (userDetails,null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				log.info("validation failed..");
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
