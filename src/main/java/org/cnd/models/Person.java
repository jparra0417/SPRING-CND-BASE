package org.cnd.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Person abstracts the model person
 * 
 * @author JParra
 *
 */
/**
 * @author User
 *
 */
@Document(collection = "person")
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
