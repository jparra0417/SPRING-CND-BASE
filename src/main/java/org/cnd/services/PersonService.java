package org.cnd.services;

import org.cnd.models.Person;

/**
 * Person service is a contract that limits the methods of person
 * 
 * @author JParra
 *
 */
public interface PersonService {
	/**
	 * Save the person
	 * 
	 * @param person
	 */
	public void save(Person person);

	/**
	 * Return a person by id
	 * 
	 * @param id
	 * @return
	 */
	public Person findById(String id);

	/**
	 * Return a person by email
	 * 
	 * @param email
	 * @return
	 */
	public Person findByEmail(String email);

}
