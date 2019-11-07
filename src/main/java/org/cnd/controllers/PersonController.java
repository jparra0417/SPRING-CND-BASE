package org.cnd.controllers;

import org.cnd.models.Person;
import org.cnd.services.PersonService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person controller is in charge of exposing person services
 * 
 * @author JParra
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController {
	private static Logger _logger = Logger.getLogger(PersonController.class);
	@Autowired
	private PersonService personService;

	/**
	 * Post service to save the person
	 * 
	 * @param person
	 * @return
	 */
	@PostMapping(path = "/save")
	public ResponseEntity<?> save(@RequestBody Person person) {
		this.personService.save(person);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Get service to get a person by email
	 * 
	 * @param person
	 * @return
	 */
	@GetMapping(path = "/getByEmail")
	public ResponseEntity<?> getByEmail(@RequestParam String email) {
		Person person = this.personService.findByEmail(email);
		if (person == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

}
