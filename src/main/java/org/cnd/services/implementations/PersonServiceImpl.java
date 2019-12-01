package org.cnd.services.implementations;

import java.util.Optional;

import org.cnd.models.Person;
import org.cnd.repositories.PersonRepository;
import org.cnd.services.PersonService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
	private static Logger _logger = Logger.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	@Override
	@Transactional
	public void save(Person person) {
		this.personRepository.save(person);
	}

	@Override
	@Transactional(readOnly = true)
	public Person findByEmail(String email) {
		Optional<Person> person = this.personRepository.findByEmail(email);
		return person.isPresent() ? person.get() : null;
	}
}
