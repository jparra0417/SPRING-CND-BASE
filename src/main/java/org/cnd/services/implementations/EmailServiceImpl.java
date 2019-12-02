package org.cnd.services.implementations;

import org.cnd.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				javaMailSender.send(msg);
			}
		});
		thread.start();
	}

}
