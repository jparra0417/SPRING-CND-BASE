package org.cnd.services;

import java.util.List;

import org.cnd.models.Email;

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
	 * It sends the email by threads
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 */
	public void sendEmail(Email email);

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

	/**
	 * It returns a list of email by sent
	 * 
	 * @param sent
	 * @return
	 */
	public List<Email> findBySent(Boolean sent);

}
