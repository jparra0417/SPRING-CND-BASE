package org.cnd.repositories;

import java.util.List;

import org.cnd.models.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Email repository is in charge of generating the basic methods to operate on
 * database
 * 
 * @author JParra
 *
 */
public interface EmailRepository extends MongoRepository<Email, String> {
	
	/**
	 * It returns a list of email by sent
	 * @param sent
	 * @return
	 */
	public List<Email> findBySent(Boolean sent);

}
