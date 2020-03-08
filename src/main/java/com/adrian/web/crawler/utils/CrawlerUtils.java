package com.adrian.web.crawler.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.validator.routines.UrlValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/*
 * Class containing util methods used along the application
 */
public class CrawlerUtils {

	/*
	 * This method compares the hostname of two URLs. Ignores wether they start with
	 * www or not
	 */
	public static boolean isSameDomain(String linkUrl, String url) {

		try {
			URI linkURI = new URI(linkUrl);
			URI uri = new URI(url);

			/*
			 * Delete the trailing www. if exists
			 */
			String linkUriString = linkURI.getHost().startsWith("www.") ? linkURI.getHost().substring(4)
					: linkURI.getHost();
			String uriString = uri.getHost().startsWith("www.") ? uri.getHost().substring(4) : uri.getHost();

			/*
			 * If both hostnames are the same, return true
			 */
			return linkUriString.equals(uriString);

		} catch (URISyntaxException e) {
			log.error("Error parsing URL. Message: {}", e.getMessage());
		}

		return false;

	}

	/*
	 * Validates if the URL is valid with Apache Commons URL Validator
	 */
	public static boolean isURLValid(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}

}
