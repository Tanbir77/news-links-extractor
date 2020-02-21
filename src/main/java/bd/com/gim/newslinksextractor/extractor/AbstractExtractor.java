package bd.com.gim.newslinksextractor.extractor;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class to define extracting action
 * 
 * @author Tanbirul Hashan
 * @since 2020-02-21
 */
public abstract class AbstractExtractor {
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** News links saved count */
	private int newsCount = 0;

	protected void startSiteUrlsExtracting(String siteUrl) {
		log.info(getFormattedMsg("Launched extractor: Fetching \"%s\" news urls", siteUrl));
		try {
			newsCount = 0;
			extractUrlsFromSite(siteUrl);
			log.info(getFormattedMsg("OK. %d news links saved: ", newsCount));
		} catch (Exception e) {
			log.error("Extractor exited abnormally", e);
		} finally {
			log.info(getFormattedMsg("Extractor exited. %d news links saved: ", newsCount));
		}
	}

	protected String getFormattedMsg(String msg, Object... args) {
		return String.format(msg, args);
	}

	protected void saveLink(String url) {
		if(log.isTraceEnabled())
			log.trace(getFormattedMsg("News [%d]. \"%s\" saved",++newsCount,url));
	}

	protected boolean isNewsLink(String str) {
		String[] parts = str.split("/");
		if (parts[parts.length - 1].trim().isEmpty())
			return false;
		else
			return parts.length > 4 && hasEncodedChars(parts[parts.length - 1]);
	}

	protected boolean hasEncodedChars(String str) {
		return Pattern.compile("[^#A-Za-z0-9-]").matcher(str).find();
	}

	protected abstract void extractUrlsFromSite(String siteUrl);
}
