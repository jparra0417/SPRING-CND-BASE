package org.cnd.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.cnd.models.Account;
import org.cnd.repositories.AccountRepository;
import org.cnd.services.AccountService;
import org.cnd.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email == null || email.isEmpty())
			throw new UsernameNotFoundException("Email is empty");
		Optional<Account> optionalAccount = this.accountRepository.findByEmail(email);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("FRONT"));

		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			return new User(account.getEmail(), account.getPassword(), true, true, true, true, authorities);
		} else
			throw new UsernameNotFoundException("Email " + email + " doesnt exist");
	}

	@Override
	public void savePassword(String email, String password) {
		Query query = new Query(Criteria.where(ConstantUtil.KEY_EMAIL).is(email));
		this.mongoTemplate.updateFirst(query, new Update().set(ConstantUtil.KEY_PASSWORD, password), Account.class);
	}

}
