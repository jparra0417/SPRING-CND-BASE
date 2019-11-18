package org.cnd.controllers;

import java.util.List;

import org.cnd.models.Person;
import org.cnd.services.PersonService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person controller is in charge of exposing person services
 * 
 * @author JParra
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController extends BaseController {
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
//	@PreAuthorize("hasAuthority('FRONT')")
	public ResponseEntity<?> save(@RequestBody Person person) {
		List<String> errors = this.validateConstraints(person);
		if (errors != null)
			return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
		this.personService.save(person);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
