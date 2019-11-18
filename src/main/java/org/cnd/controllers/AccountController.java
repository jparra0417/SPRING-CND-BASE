package org.cnd.controllers;

import java.util.List;

import org.cnd.models.Account;
import org.cnd.services.AccountService;
import org.cnd.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class AccountController extends BaseController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(path = "/savePassword")
	public ResponseEntity<?> save(@RequestBody Account account) {
		List<String> errors = this.validateConstraints(account, ConstantUtil.KEY_EMAIL, ConstantUtil.KEY_PASSWORD);
		if (errors != null)
			return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
		this.accountService.savePassword(account.getEmail(), bCryptPasswordEncoder.encode(account.getPassword()));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
