package org.cnd.services;

/**
 * Capacities of the account service
 * 
 * @author User
 *
 */
public interface AccountService {
	/**
	 * It saves the password by email
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public void savePassword(String email, String password);
}
