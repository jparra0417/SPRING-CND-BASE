package org.cnd.controllers;

import java.util.List;
import java.util.TreeMap;

import org.cnd.models.Account;
import org.cnd.services.AccountService;
import org.cnd.services.EmailService;
import org.cnd.services.HashService;
import org.cnd.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class AccountController extends BaseController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private HashService hashService;

	@Autowired
	private EmailService emailService;

	@PostMapping(path = "/savePasswordByToken")
	public ResponseEntity<?> save(@RequestParam(required = true, value = AppConstant.KEY_HIDDEN_HASH) String hash,
			@RequestBody(required = true) Account account) {

		// validate fields
		List<String> errors = this.validateConstraints(account, AppConstant.KEY_TOKEN, AppConstant.KEY_PASSWORD);
		if (errors != null)
			return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);

		// validate hash
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put(AppConstant.KEY_TOKEN, account.getToken());
		if (account.getEnable() != null)
			map.put(AppConstant.KEY_ENABLE, account.getEnable());

		if (!hashService.validate(hash, map))
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_HASH_INTEGRITY },
					HttpStatus.BAD_REQUEST);
		// create password
		String password = bCryptPasswordEncoder.encode(account.getPassword());
		boolean result = this.accountService.savePasswordByToken(account.getToken(), password, account.getEnable());
		if (result)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_KEY_ACCOUNT_PASSWORD_WAS_NOT_SAVED },
					HttpStatus.BAD_REQUEST);
	}

	/**
	 * Post service to save the person
	 * 
	 * @param person
	 * @return
	 */
	@PostMapping(path = "/resetPassword")
	public ResponseEntity<?> signUp(@RequestBody Account account) {

		// validate all fields
		List<String> errors = this.validateConstraints(account, AppConstant.KEY_EMAIL);
		if (errors != null)
			return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);

		// validate if email is unique data
		account = this.accountService.findByEmail(account.getEmail());
		if (account == null)
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_KEY_PERSON_EMAIL_DOES_NOT_EXIST },
					HttpStatus.BAD_REQUEST);			
			
		// resolve if the account has to enable the account when the password will be
		// reseted because was the first time
		Boolean enable = null;
		if (account.getEnable() == null && account.getPassword() == null)
			enable = true;

		// create token and hash for the account
		TreeMap<String, Object> hash = this.accountService.createHashTokenByEmail(account.getEmail(), enable);

		// send email
		this.emailService.sendEmailResetPassword(account.getEmail(), account.getFirstName(),
				hash.get(AppConstant.KEY_HIDDEN_HASH).toString(), hash.get(AppConstant.KEY_TOKEN).toString(), enable);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
