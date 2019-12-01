package org.cnd.services.implementations;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import org.cnd.services.HashService;
import org.cnd.util.AppConstant;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HashServiceImpl implements HashService {
	private static Logger _logger = Logger.getLogger(HashServiceImpl.class);

	@Value("${security.hash.salt}")
	private String securityHashSalt;

	@Override
	@Transactional(readOnly = true)
	public String create(TreeMap<String, Object> map) {
		if (map == null || map.isEmpty())
			return null;
		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance(AppConstant.ALGORITHM_MD5);
			String input = new StringBuilder(map.toString()).append(securityHashSalt).toString();
			digest.update(input.getBytes(), 0, input.length());
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			_logger.error("Error trying to create hash", e);
			return null;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Boolean validate(String hash, TreeMap<String, Object> map) {
		return hash != null && hash.equals(create(map));
	}

}
