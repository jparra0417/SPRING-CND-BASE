package org.cnd.models;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Abstraction of person
 * 
 * @author JParra
 *
 */
@Document(collection = "email")
public class Email extends Base implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5709149814090521912L;

	private String[] to;
	private String subject;
	private String body;
	private Boolean sent;

	public Email() {
		super();
	}

	public Email(String[] to, String subject, String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the sent
	 */
	public Boolean getSent() {
		return sent;
	}

	/**
	 * @param sent the sent to set
	 */
	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	@Override
	public String toString() {
		return "Email [to=" + to + ", subject=" + subject + ", body=" + body + ", sent=" + sent + "]";
	}

}
