package org.cnd.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.cnd.util.AppConstant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Abstract the model person
 * 
 * @author JParra
 *
 */
public class Account extends Person {

	/** serial version UID */
	private static final long serialVersionUID = -7247377676038338735L;

	/** constructor */
	private List<String> authorities;
	private Boolean enable;
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = AppConstant.ERROR_KEY_ACCOUNT_PASSWORD_EMPTY)
	private String password;
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = AppConstant.ERROR_KEY_ACCOUNT_TOKEN_EMPTY)
	private String token;

	public Account() {
		super();
	}

	/**
	 * @return the authorities
	 */
	public List<String> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the enable
	 */
	public Boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
