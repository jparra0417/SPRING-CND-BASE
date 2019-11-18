package org.cnd.repositories;

import org.cnd.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Person repository is in charge of generating the basic methods to operate on
 * database
 * 
 * @author JParra
 *
 */
public interface PersonRepository extends MongoRepository<Person, String> {
	
}
