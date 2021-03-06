package org.cnd.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cnd.services.JwtService;
import org.cnd.util.AppConstant;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	/** It allows to authenticate */
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;

	public AuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		setRequiresAuthenticationRequestMatcher(
				new AntPathRequestMatcher(AppConstant.URL_ACCOUNT_SIGNIN, AppConstant.POST));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String email = request.getParameter(AppConstant.KEY_EMAIL);
		String password = request.getParameter(AppConstant.KEY_PASSWORD);

		if (email == null || password == null) {
			try {
				Map<String, String> user = new ObjectMapper().readValue(request.getInputStream(), Map.class);
				if (user != null) {
					email = user.get(AppConstant.KEY_EMAIL);
					password = user.get(AppConstant.KEY_PASSWORD);
				}

			} catch (IOException e) {
				logger.error("Error to try parse json to user", e);
			}
		}

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
		return this.authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		String token = this.jwtService.create(authentication);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("jwt", token);

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		if (failed instanceof DisabledException)
			response.getWriter().write(
					new ObjectMapper().writeValueAsString(new String[] { AppConstant.ERROR_KEY_ACCOUNT_DISABLED }));
		else
			response.getWriter().write(
					new ObjectMapper().writeValueAsString(new String[] { AppConstant.ERROR_KEY_ACCOUNT_SIGNIN }));
		response.setStatus(401);
		response.setContentType("application/json");
	}
}
