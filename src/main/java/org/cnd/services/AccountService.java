package org.cnd.services;

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
	 * Save a password according to token
	 * 
	 * @param token
	 * @param password
	 */
	public boolean savePasswordByToken(String token, String password);

}
