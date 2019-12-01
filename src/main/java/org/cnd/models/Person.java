package org.cnd.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.cnd.util.AppConstant;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Abstraction of person
 * @author JParra
 *
 */
@Document(collection = "person")
public class Person extends Base implements Serializable {

	/** serial version UID */
	private static final long serialVersionUID = -4352435119454271653L;

	/** attributes */
	@NotEmpty(message = AppConstant.ERROR_KEY_PERSON_FIRST_NAME_EMPTY)
	@Length(max = 200, message = AppConstant.ERROR_KEY_PERSON_FIRST_NAME_LENGTH)
	private String firstName;

	@NotEmpty(message = AppConstant.ERROR_KEY_PERSON_LAST_NAME_EMPTY)
	@Length(max = 200, message = AppConstant.ERROR_KEY_PERSON_LAST_NAME_LENGTH)
	private String lastName;
	
	@NotEmpty(message = AppConstant.ERROR_KEY_PERSON_EMAIL_EMPTY)
	@Length(max = 100, message = AppConstant.ERROR_KEY_PERSON_EMAIL_LENGTH)
	@Email(message = AppConstant.ERROR_KEY_PERSON_EMAIL_EMAIL)
	private String email;

	/** constructor */
	public Person() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

}
