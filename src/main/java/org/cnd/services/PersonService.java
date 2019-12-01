package org.cnd.services;

import org.cnd.models.Person;

/**
 * Capacities of the person service
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
	public Person findByEmail(String email);	

}
