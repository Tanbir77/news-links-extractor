package bd.com.gim.newslinksextractor.extractor;

import java.io.IOException;
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
	private int savedCount = 0;
	/** Store the links iteration index to track last saved news index */
	protected int linksIterationIndx = 0;

	protected void startSiteUrlsExtracting(String siteUrl) {
		log.info(getFormattedMsg("Launched extractor: Fetching \"%s\" news urls", siteUrl));
		try {
			extractUrlsFromSite(siteUrl);
		} catch (Exception e) {
			log.error("Extractor exited abnormally", e);
			log.info(getFormattedMsg("Saving all pending extracted news links....."));
			savePendingNewsLinks(getAllExtactedLinks());
		} finally {
			log.info(getFormattedMsg("Extractor exited. %d news links saved: ", savedCount));
		}
	}

	private void savePendingNewsLinks(String[] links) {
		for(int i = linksIterationIndx+1 ; i < links.length; i++) {
			if (isNewsLink(links[linksIterationIndx]))
				saveNewsLink(links[linksIterationIndx]);
		}
		
	}

	protected String getFormattedMsg(String msg, Object... args) {
		return String.format(msg, args);
	}

	protected void saveNewsLink(String url) {
		if (log.isTraceEnabled())
			log.trace(getFormattedMsg("News [%d]. \"%s\" saved", ++savedCount, url));
	}

	/** Check if a link is a news link or not. Typically, all news links are encoded.
	 * @return true if, and only if, and only if links contains any encoded characters 
	 * */
	protected boolean isNewsLink(String link) {
		String[] parts = link.split("/");
		if (parts[parts.length - 1].trim().isEmpty())
			return false;
		else
			return parts.length > 4 && hasEncodedChars(parts[parts.length - 1]);
	}
	
	private boolean hasEncodedChars(String str) {
		return Pattern.compile("[^#A-Za-z0-9-]").matcher(str).find();
	}

	protected abstract void extractUrlsFromSite(String siteUrl) throws IOException, InterruptedException;

	protected abstract String[] getAllExtactedLinks();
}
