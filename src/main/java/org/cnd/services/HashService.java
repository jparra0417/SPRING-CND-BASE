package org.cnd.services;

import java.util.TreeMap;

/**
 * It defines the scope of hash service
 * 
 * @author JParra
 *
 */
public interface HashService {
	/**
	 * It creates a hash
	 * 
	 * @param map
	 * @return
	 */
	public String create(TreeMap<String, Object> map);

	/**
	 * Validate if hash is correct
	 * 
	 * @param hash
	 * @param map
	 * @return
	 */
	public Boolean validate(String hash, TreeMap<String, Object> map);

}
