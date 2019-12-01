package org.cnd.repositories;

import java.util.Optional;

import org.cnd.models.Account;
//import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Account repository is in charge of generating the basic methods to operate on
 * database
 * 
 * @author JParra
 *
 */
public interface AccountRepository extends MongoRepository<Account, String> {

	/**
	 * Return an account by email
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Account> findByEmail(String email);

}
