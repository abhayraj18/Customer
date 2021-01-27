package com.example.config.security;

/**
 * This class has all the URLs which doesn't require authentication.
 * 
 * @author abhay.jain
 *
 */
public class PublicURL {

	public static final String[] GENERIC_URLS = new String[] { "/favicon.ico", "/eureka/**" };
	public static final String[] AUTHETICATION_URLS = new String[] { "/authentication/**" };
	public static final String[] USER_URLS = new String[] { "/user/create" };
}
