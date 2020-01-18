package org.cnd.services;

import java.util.TreeMap;

import org.cnd.models.Account;

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
	 * 
	 * @param email
	 * @param enable - determine if the token enables the account
	 * @return
	 */
	public TreeMap<String, Object> createHashTokenByEmail(String email, Boolean enable);

	/**
	 * Save a password according to token
	 * 
	 * @param token
	 * @param password
	 * @param enable - it determines if the account should be enabled
	 */
	public boolean savePasswordByToken(String token, String password, Boolean enable);

	/**
	 * Return a person by id
	 * 
	 * @param id
	 * @return
	 */
	public Account findByEmail(String email);

}
