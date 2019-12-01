package org.cnd.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cnd.services.JwtService;
import org.cnd.util.AppConstant;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private JwtService jwtService;

	public AuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(AppConstant.KEY_HEADER_AUTHORIZATION);
		if (!this.jwtService.validate(header)) {
			res.setStatus(403);
			return;
		}

		UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
				this.jwtService.getUsername(header), AppConstant.EMPTY, this.jwtService.getAuthorities(header));
		SecurityContextHolder.getContext().setAuthentication(userAuth);
		chain.doFilter(req, res);

	}

}
