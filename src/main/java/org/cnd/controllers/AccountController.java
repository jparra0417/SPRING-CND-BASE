package org.cnd.controllers;

import java.util.List;
import java.util.TreeMap;

import org.cnd.models.Account;
import org.cnd.services.AccountService;
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
		if (!hashService.validate(hash, map))
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_HASH_INTEGRITY },
					HttpStatus.BAD_REQUEST);

		if (this.accountService.savePasswordByToken(account.getToken(),
				bCryptPasswordEncoder.encode(account.getPassword())))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<String[]>(new String[] { AppConstant.ERROR_KEY_ACCOUNT_PASSWORD_WAS_NOT_SAVED },
					HttpStatus.BAD_REQUEST);
	}
}
