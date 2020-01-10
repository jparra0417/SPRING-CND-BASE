package org.cnd.services.implementations;

import java.text.MessageFormat;

import javax.mail.internet.MimeMessage;

import org.cnd.models.Email;
import org.cnd.repositories.EmailRepository;
import org.cnd.services.EmailService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private static Logger _logger = Logger.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailRepository emailRepository;

	@Value("${email.signup.body}")
	private String emailSingupBody;

	@Value("${email.signup.subject}")
	private String emailSingupSubject;

	@Override
	public void sendEmail(String subject, String body, String... to) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				Email email = new Email(to, subject, body);
				try {
					MimeMessage message = javaMailSender.createMimeMessage();
					message.setSubject(subject);
					MimeMessageHelper helper;
					helper = new MimeMessageHelper(message, true);
					helper.setTo(to);
					helper.setText(body, true);
					
					javaMailSender.send(message);
					email.setSent(true);
				} catch (Exception ex) {
					_logger.error("Error trying to send email", ex);
					email.setSent(false);
				} finally {
					emailRepository.save(email);
				}

			}
		});
		thread.start();
	}

	@Override
	public void sendEmailSignup(String to, String name, String hash, String token) {
		String body = MessageFormat.format(emailSingupBody, name, hash, token);
		String subject = emailSingupSubject;
		this.sendEmail(subject, body, to);
	}
}
