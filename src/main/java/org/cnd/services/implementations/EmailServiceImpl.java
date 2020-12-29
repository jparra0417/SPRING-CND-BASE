package org.cnd.services.implementations;

import java.text.MessageFormat;
import java.util.List;

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

	@Value("${email.reset.password.body}")
	private String emailResetPasswordBody;

	@Value("${email.reset.password.subject}")
	private String emailResetPasswordSubject;

	@Value("${frontend.base}")
	private String frontendBase;

	@Override
	public void sendEmail(String subject, String body, String... to) {
		Email email = new Email(to, subject, body);
		this.sendEmail(email);

	}

	@Override
	public void sendEmailSignup(String to, String name, String hash, String token) {
		String body = MessageFormat.format(emailSingupBody, name, frontendBase, hash, token, true);
		String subject = emailSingupSubject;
		this.sendEmail(subject, body, to);
	}

	@Override
	public void sendEmailResetPassword(String to, String name, String hash, String token, Boolean enable) {
		String body = MessageFormat.format(emailResetPasswordBody, name, frontendBase, hash, token,
				enable == null ? "" : "/" + enable);
		String subject = emailResetPasswordSubject;
		this.sendEmail(subject, body, to);

	}

	@Override
	public void sendEmail(Email email) {
		if (email != null) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						MimeMessage message = javaMailSender.createMimeMessage();
						message.setSubject(email.getSubject());
						MimeMessageHelper helper;
						helper = new MimeMessageHelper(message, true);
						helper.setTo(email.getTo());
						helper.setText(email.getBody(), true);

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

	}

	@Override
	public List<Email> findBySent(Boolean sent) {
		return this.emailRepository.findBySent(sent);
	}
}
