package com.snhu.app.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UriUtil
 */
@Component
public class UriUtil {

	@Autowired
	Logger log;

	@Autowired
	HttpServletRequest request;

	public URL getBaseUrl() throws MalformedURLException {
		String baseUrl = request.getRequestURL()
			.reverse()
			.delete(0, request.getRequestURI().length())
			.reverse()
			.append('/').toString();
		return new URL( baseUrl );
	}
}