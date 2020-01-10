package org.cnd.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import org.cnd.models.Account;
import org.cnd.repositories.AccountRepository;
import org.cnd.services.AccountService;
import org.cnd.services.HashService;
import org.cnd.util.AppConstant;
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

import com.mongodb.client.result.UpdateResult;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private HashService hashService;

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
			return new User(account.getEmail(), (account.getPassword() == null ? "" : account.getPassword()),
					account.getEnable() != null && account.getEnable(), true, true, true, authorities);

		}
		throw new UsernameNotFoundException("Email " + email + " doesnt exist");
	}

	@Override
	@Transactional
	public String createTokenByEmail(String email) {
		String token = UUID.randomUUID().toString();
		Query query = new Query(Criteria.where(AppConstant.KEY_EMAIL).is(email));
		this.mongoTemplate.updateFirst(query, new Update().set(AppConstant.KEY_TOKEN, token), Account.class);
		return token;
	}

	@Override
	@Transactional
	public boolean savePasswordByToken(String token, String password) {
		Query query = new Query(Criteria.where(AppConstant.KEY_TOKEN).is(token));
		UpdateResult updateResult = this.mongoTemplate.updateFirst(query,
				new Update().set(AppConstant.KEY_PASSWORD, password)
						.set(AppConstant.KEY_TOKEN, UUID.randomUUID().toString()).set(AppConstant.KEY_ENABLE, true),
				Account.class);
		return updateResult != null && updateResult.getModifiedCount() > 0;
	}

	@Override
	@Transactional
	public TreeMap<String, Object> createHashTokenByEmail(String email) {
		String token = this.createTokenByEmail(email);
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put(AppConstant.KEY_TOKEN, token);
		map.put(AppConstant.KEY_HIDDEN_HASH, hashService.create(map));
		return map;
	}

}
