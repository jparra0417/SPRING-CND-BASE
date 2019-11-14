package org.cnd.repositories;

import java.util.Optional;

import org.cnd.models.Person;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Person repository is in charge of generating the basic methods to operate on
 * database
 * 
 * @author JParra
 *
 */
public interface PersonRepository extends MongoRepository<Person, String> {
	/**
	 * Return a person by email
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Person> findByEmail(String email);

	/**
	 * Return a person by the email and password
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Aggregation(pipeline = { "{ $match : { email: ?0 , password : ?1 } }" })
	public Optional<Person> findByEmailAndPassword(String email, String password);
}
