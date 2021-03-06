package bd.com.gim.newslinksextractor.extractor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bd.com.gim.newslinksextractor.model.News;
import bd.com.gim.newslinksextractor.service.NewsService;
import bd.com.gim.newslinksextractor.service.NewsServiceImpl;

/**
 * Abstract class to define extracting action
 * 
 * @author Tanbirul Hashan
 * @since 2020-02-21
 */
public abstract class AbstractExtractor {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private NewsService newsServ;

	/** News links saved count */
	private int savedCount = 0;
	/** Store the links iteration index to track last saved news index */
	protected int linksArrIndx = 0;

	protected void startSiteUrlsExtracting(String siteUrl) {
		log.info(String.format("Launched extractor: Fetching \"%s\" news urls", siteUrl));
		try {
			extractUrlsFromSite(siteUrl);
		} catch (Exception e) {
			log.error("Extractor exited abnormally", e);
			log.info(String.format("Saving all pending extracted news links....."));
			savePendingNewsLinks(getAllExtactedLinks());
		} finally {
			log.info("Extractor exited");
			log.info(String.format("%d news links from %d links are saved: ", savedCount,getAllExtactedLinks().length));
		}
	}

	private void savePendingNewsLinks(String[] links) {
		for (int i = linksArrIndx + 1; i < links.length; i++) {
			if (isNewsLink(links[linksArrIndx]))
				saveNewsLink(getNormalizeNewsUrl(links[linksArrIndx]));
		}
	}

	protected void saveNewsLink(String url) {
		if (newsServ == null)
			newsServ = new NewsServiceImpl();

		if (!isNewsInDb(url)) {
			if (newsServ.insert(getPreparedEntity(url)) != null) {
				savedCount++;
				if (log.isTraceEnabled())
					log.trace(String.format("News [%d]. \"%s\" saved", savedCount, url));
			}
			
		}
	}
	
	protected boolean isNewsInDb(String url) {
		return newsServ.isNewsExistsInDB(url);
	}
	
	/**
	 * Check if a link is a news link or not. Typically, all news links are encoded.
	 * 
	 * @return true if, and only if, and only if links contains any encoded
	 *         characters
	 */
	protected boolean isNewsLink(String url) {
		String[] parts = url.split("/");
		if (parts[parts.length - 1].trim().isEmpty())
			return false;
		else if(parts[parts.length - 1].contains("="))
			return false;
		return parts.length > 4 && hasEncodedChars(parts[parts.length - 1]);
	}

	private boolean hasEncodedChars(String str) {
		return Pattern.compile("[^#,=,?,&A-Za-z0-9-]").matcher(str).find();
	}

	private News getPreparedEntity(String url) {
		String[] parts = url.split("/");
		return new News()
				.setCategory(parts[3])
				.setTitleSum(getDecodedStr(parts[parts.length - 1]))
				.setUrl(url);
	}

	private String getDecodedStr(String str) {
		try {
			return URLDecoder.decode(str, StandardCharsets.UTF_8.toString())
					.replace("-", " ")
					.replace("#comments", "");
		} catch (UnsupportedEncodingException e) {
			log.warn(String.format("\"%s\" failed to decode", str), e);
			return null;
		}
	}
	
	protected String getNormalizeNewsUrl(String url) {
		return url.replace("#comments", "");
	}

	protected abstract void extractUrlsFromSite(String siteUrl) throws IOException, InterruptedException;

	protected abstract String[] getAllExtactedLinks();
}
