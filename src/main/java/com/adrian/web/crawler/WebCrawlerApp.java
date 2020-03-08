package com.adrian.web.crawler;

import java.util.ArrayList;
import java.util.List;

import com.adrian.web.crawler.crawler.CrawlerManager;
import com.adrian.web.crawler.crawler.RobotsParser;
import com.adrian.web.crawler.model.CrawlerCustomException;
import com.adrian.web.crawler.model.Sitemap;
import com.adrian.web.crawler.utils.CrawlerUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebCrawlerApp {

	private static final Sitemap sitemap = new Sitemap();

	public static void main(String[] args) {

		/*
		 * If all parameters are not passed, return an error
		 */
		if (args.length < 2) {
			log.error("Need to pass 3 arguments! {url} {number of threads} {read robots.txt (boolean)}");
			throw new CrawlerCustomException(
					"Need to pass 3 arguments! {url} {number of threads} {read robots.txt (boolean)}");
		}

		String url = null;
		int n = 0;
		boolean useRobots = false;

		try {
			url = args[0];
			/*
			 * If URL is not valid, show error and stop the program
			 */
			if (!CrawlerUtils.isURLValid(url)) {
				log.error("URL is not valid!");
				throw new CrawlerCustomException("URL is not valid!");
			}
			n = Integer.parseInt(args[1]);
			/*
			 * If the number of threads is less than one, show error and stop the program
			 */
			if (n < 1) {
				log.error("The number of threads must be bigger than 0!");
				throw new CrawlerCustomException("The number of threads must be bigger than 0!");
			}
			useRobots = Boolean.parseBoolean(args[2]);
		} catch (Exception e) {
			log.error("At least one of the arguments is wrong!");
			throw new CrawlerCustomException("At least one of the arguments is wrong!");
		}

		/*
		 * Create the list of disallowed URLs if the argument is true
		 */
		List<String> disallowedURLs = new ArrayList<>();
		if (useRobots)
			disallowedURLs = RobotsParser.checkRobotsTxt(url);

		/*
		 * Create a new Crawler Manager and call the startCrawling method
		 */
		CrawlerManager crawlerManager = new CrawlerManager(url, n, disallowedURLs, sitemap);
		Sitemap sitemap = crawlerManager.startCrawling();

		printResult(sitemap);

	}

	private static void printResult(Sitemap sitemap) {

		/*
		 * System.out.println("**********************"); for (Page page :
		 * sitemap.getPages()) { System.out.println(page.getUrl()); if (page.getLinks()
		 * != null) System.out.println(page.getLinks().toString()); }
		 */

	}

}
