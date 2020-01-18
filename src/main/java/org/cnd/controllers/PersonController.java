package org.cnd.controllers;

import java.util.List;
import java.util.TreeMap;

import org.cnd.models.Person;
import org.cnd.services.AccountService;
import org.cnd.services.EmailService;
import org.cnd.services.PersonService;
import org.cnd.util.AppConstant;
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
	@Autowired
	private AccountService accountService;
	@Autowired
	private EmailService emailService;

	/**
	 * Post service to save the person
	 * 
	 * @param person
	 * @return
	 */
	@PostMapping(path = "/signup")
	public ResponseEntity<?> signUp(@RequestBody Person person) {

		// validate all fields
		List<String> errors = this.validateConstraints(person);
		if (errors != null)
			return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);

		// validate if email is unique data
		Person personEmail = this.personService.findByEmail(person.getEmail());
		if (personEmail != null)
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_KEY_PERSON_EMAIL_NOT_UNIQUE },
					HttpStatus.BAD_REQUEST);

		// save person
		this.personService.save(person);

		// create token and hash for the account
		TreeMap<String, Object> hash = this.accountService.createHashTokenByEmail(person.getEmail(), true);

		// send email
		this.emailService.sendEmailSignup(person.getEmail(), person.getFirstName(),
				hash.get(AppConstant.KEY_HIDDEN_HASH).toString(), hash.get(AppConstant.KEY_TOKEN).toString());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
