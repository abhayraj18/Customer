package com.example.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);
	private static final String ACCESS_DENIED_MESSAGE = "Sorry, You're not authorized to access this resource.";

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		System.out.println(httpServletRequest.getRequestURI());
		logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ACCESS_DENIED_MESSAGE);
	}
}
