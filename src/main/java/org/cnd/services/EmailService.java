package org.cnd.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	/**
	 * It sends the email by threads
	 * 
	 * @param msg
	 */
	public void sendEmail(SimpleMailMessage msg);
}
