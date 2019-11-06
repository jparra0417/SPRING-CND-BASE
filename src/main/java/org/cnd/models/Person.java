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
	@NotEmpty(message = "person.errorNameNotEmpty")
	@Length(max = 200, message = "person.errorNameLength")
	private String name;

	@NotEmpty(message = "person.errorLastNameNotEmpty")
	@Length(max = 200, message = "person.errorLastnameLength")
	private String lastname;

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

	/** methods */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
		return "Person [name=" + name + ", lastname=" + lastname + ", email=" + email + "]";
	}

}
