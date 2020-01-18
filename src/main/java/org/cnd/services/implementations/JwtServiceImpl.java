package org.cnd.services.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.cnd.models.Account;
import org.cnd.repositories.AccountRepository;
import org.cnd.services.JwtService;
import org.cnd.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
	AccountRepository accountRepository;

	@Value("${security.jwt.key}")
	private String securityJwtKey;

	@Value("${security.jwt.expiration}")
	private int expiration;

	@Override
	public String create(Authentication authentication) throws JsonProcessingException {

		Claims claims = Jwts.claims();
		// get the authorities
		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
		StringBuilder roleSb = new StringBuilder();
		if (roles != null) {
			String delim = AppConstant.EMPTY;
			for (GrantedAuthority grantedAuthority : roles) {
				roleSb.append(delim).append(grantedAuthority.getAuthority());
				delim = AppConstant.COMMA;
			}
		}
		claims.put(AppConstant.KEY_AUTHORITIES, roleSb.toString());

		// get the account of the user
		Optional<Account> optionalAccount = this.accountRepository
				.findByEmail(((User) authentication.getPrincipal()).getUsername());		
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			claims.put(AppConstant.KEY_NAME, account.getFirstName());
			claims.setSubject(account.getId());
		}

		Calendar calendarExpiration = Calendar.getInstance();
		calendarExpiration.add(Calendar.MILLISECOND, expiration);

		return Jwts.builder().setIssuedAt(Calendar.getInstance().getTime()).setClaims(claims)
				.setExpiration(calendarExpiration.getTime())
				.signWith(SignatureAlgorithm.HS512, securityJwtKey.getBytes()).compact();
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(securityJwtKey.getBytes()).parseClaimsJws(resolve(token)).getBody();
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public List<GrantedAuthority> getAuthorities(String strToken) throws IOException {
		Claims token = getClaims(strToken);
		String strRole = (String) token.get(AppConstant.KEY_AUTHORITIES);
		if (strRole == null || strRole.isEmpty())
			return null;

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String[] roles = strRole.split(AppConstant.COMMA);
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;

	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(AppConstant.SECURITY_TOKEN_PREFIX))
			return token.replace(AppConstant.SECURITY_TOKEN_PREFIX, "");
		return null;
	}
}
