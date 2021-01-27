package com.example.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.util.RequestParameter;

public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationTokenProvider tokenProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Get auth token from request and validate it.
			String authToken = getAuthTokenFromRequest(request);
			if (StringUtils.isNotBlank(authToken) && tokenProvider.validateToken(authToken)) {
				// Get id from auth token
				String id = tokenProvider.getIdFromAuthToken(authToken);
				UserDetails userDetails = userDetailsService.loadUserById(Integer.parseInt(id));
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set authentication in security context: ", ex);
		}
		filterChain.doFilter(request, response);
	}

	private String getAuthTokenFromRequest(HttpServletRequest request) {
		return request.getHeader(RequestParameter.AUTHORIZATION);
	}
}
