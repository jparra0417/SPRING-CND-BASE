package org.cnd.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Person abstracts the model person
 * 
 * @author JParra
 *
 */
public class Person extends Base implements Serializable {

	/** serial version UID */
	private static final long serialVersionUID = -4352435119454271653L;

	/** attributes */
	@NotEmpty(message = "person.errorFirstNameNotEmpty")
	@Length(max = 200, message = "person.errorFirstNameLength")
	private String firstName;

	@NotEmpty(message = "person.errorLastNameNotEmpty")
	@Length(max = 200, message = "person.errorLastNameLength")
	private String lastName;

	@Length(max = 100, message = "person.errorEmailLength")
	@Email(message = "person.errorEmailEmail")
	private String email;

	@NotEmpty(message = "person.errorPasswordNotEmpty")
	@Length(max = 100, message = "person.errorPasswordLength")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + "]";
	}

}
