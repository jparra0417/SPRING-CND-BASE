package org.cnd.util;

public class AppConstant {
	/** common */
	public static final String COMMA = ",";
	public static final String EMPTY = "";

	/** keys */
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_HIDDEN_HASH = "_h";

	/** security */
	public final static String SECURITY_TOKEN_PREFIX = "Bearer ";
	public static final String URL_ACCOUNT_LOGIN = "/account/login";
	public static final String URL_ACCOUNT_SAVE_PASSWORD_BY_TOKEN = "/account/savePasswordByToken";
	public static final String URL_ACCOUNT_SIGN_UP = "/person/signUp";
	public static final String POST = "POST";
	public static final String KEY_AUTHORITIES = "auth";
	public static final String KEY_HEADER_AUTHORIZATION = "Authorization";
	public static final String ALGORITHM_MD5 = "MD5";

	/** error person */
	public static final String ERROR_KEY_PERSON_FIRST_NAME_EMPTY = "person.errorFirstNameEmpty";
	public static final String ERROR_KEY_PERSON_FIRST_NAME_LENGTH = "person.errorFirstNameLength";
	public static final String ERROR_KEY_PERSON_LAST_NAME_EMPTY = "person.errorLastNameEmpty";
	public static final String ERROR_KEY_PERSON_LAST_NAME_LENGTH = "person.errorLastNameLength";
	public static final String ERROR_KEY_PERSON_EMAIL_LENGTH = "person.errorEmailLength";
	public static final String ERROR_KEY_PERSON_EMAIL_EMAIL = "person.errorEmailEmail";
	public static final String ERROR_KEY_PERSON_EMAIL_EMPTY = "person.errorEmailEmpty";
	public static final String ERROR_KEY_PERSON_EMAIL_NOT_UNIQUE = "person.errorEmailNotUnique";

	/** error account */
	public static final String ERROR_KEY_ACCOUNT_PASSWORD_EMPTY = "account.errorPasswordEmpty";
	public static final String ERROR_KEY_ACCOUNT_TOKEN_EMPTY = "account.errorTokenEmpty";
	public static final String ERROR_KEY_ACCOUNT_PASSWORD_WAS_NOT_SAVED = "account.errorPasswordWasNotSaved";

	/** error login */
	public static final String ERROR_KEY_LOGIN_FAILED = "login.errorFailed";

	/** error hash integrity */
	public static final String ERROR_HASH_INTEGRITY = "hash.errorIntegrity";

}
