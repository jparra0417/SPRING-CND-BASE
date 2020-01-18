package org.cnd.services;

public interface EmailService {
	/**
	 * It sends the email by threads
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 */
	public void sendEmail(String subject, String body, String... to);

	/**
	 * It sends email when someone has signed up
	 * 
	 * @param to
	 * @param name
	 * @param hash
	 * @param token
	 */
	public void sendEmailSignup(String to, String name, String hash, String token);

	/**
	 * It sends email when someone has reseted the password
	 * 
	 * @param to
	 * @param name
	 * @param hash
	 * @param token
	 * @param enable
	 */
	public void sendEmailResetPassword(String to, String name, String hash, String token, Boolean enable);

}
