package org.cnd.repositories;

import java.util.Optional;

import org.cnd.models.Account;
//import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

	/**
	 * Return an account by email
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Account> findByEmail(String email);

//	/**
//	 * Return a person by the email and password
//	 * 
//	 * @param email
//	 * @param password
//	 * @return
//	 */
//	@Aggregation(pipeline = { "{ $match : { email: ?0 , password : ?1 } }" })
//	public Optional<Person> findByEmailAndPassword(String email, String password);

}
