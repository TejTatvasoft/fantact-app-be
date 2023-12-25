package com.example.demo.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	UserRepository usersRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			 String jwtToken = getTokenFromRequest(request);
		     if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
		    	 String email = jwtTokenProvider.getUsername(jwtToken);
		         UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		         authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		         User user = usersRepository.findByEmail(email);
		         request.setAttribute("userId", user.getUserId());
		     }
		}catch(Exception e) {
			LOGGER.error("Could not set user authentication in security context", e);;
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
