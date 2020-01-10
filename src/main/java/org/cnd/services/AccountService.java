package org.cnd.services;

import java.util.TreeMap;

/**
 * Scope of account service
 * 
 * @author JParra
 *
 */
public interface AccountService {

	/**
	 * Reset a token according to email
	 * 
	 * @param email
	 */
	public String createTokenByEmail(String email);

	/**
	 * Create a hash and token by the email of the account
	 * @param email
	 * @return
	 */
	public TreeMap<String, Object> createHashTokenByEmail(String email);

	/**
	 * Save a password according to token
	 * 
	 * @param token
	 * @param password
	 */
	public boolean savePasswordByToken(String token, String password);

}
