package bd.com.gim.newslinksextractor.extractor;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * News Links extractor class.
 * Targeted news site is <a href="https://www.prothomalo.com/">prothom alo</a>.

 * @author Tanbirul Hashan
 * @since 2020-02-21
 */
public class Extractor extends AbstractExtractor{
	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final String URL = "https://www.prothomalo.com/";
	private static final String CSS_QUERY = "a[abs:href~=(https|http)://www.prothomalo.com/]";
	/** Set of unique links */
	private Set<String> linksSet = new LinkedHashSet<>();
	/**
	 * Retry counter in case of re-trying extraction if extracting interrupted by
	 * unintended exception
	 */
	private int retry = 0;

	public void ExtractUrls() {
		startSiteUrlsExtracting(URL);
	}

	@Override
	public void extractUrlsFromSite(String siteUrl) throws IOException, InterruptedException {
		collectUrls(siteUrl);

		String[] links = linksSet.toArray(new String[linksSet.size()]);

		for (linksArrIndx = 0; linksArrIndx < links.length; linksArrIndx++) {
			if (isNewsLink(links[linksArrIndx]))
				saveNewsLink(getNormalizeNewsUrl(links[linksArrIndx]));
			else {
				collectUrls(links[linksArrIndx]);
				links = linksSet.toArray(new String[linksSet.size()]);
			}
		}

	}

	private void collectUrls(String url) throws IOException, InterruptedException {
		log.info(String.format("Fetching \"%s\" news links", url));

		Elements hrefEls = new Elements();

		try {
			Document doc = Jsoup.connect(url).timeout(60 * 1000).execute().parse();
			hrefEls = doc.select(CSS_QUERY);

			/* Sleeping to avoid frequent request which will prevent from IP blacklisting */
			Thread.sleep(3 * 1000l);
		} catch (IOException | InterruptedException e) {
			/*Re-trying for maximum 10 times if extraction interrupted by unintended exception */
			if (++retry < 10)
				collectUrls(url);
			else
				throw e;
		}

		linksSet.addAll(getFilteredLinks(hrefEls));
	}
	
	/** Filtering unusual links that does not contain any news/unique news 
	 *  @param elements {@link Elements} instance
	 *  @return A set of valid links that contains news */
	private Set<String> getFilteredLinks(Elements elements) {
		return elements.stream()
				.filter(el -> !el.attr("abs:href").contains("/home"))
				.filter(el -> !el.attr("abs:href").contains("/photo"))
				.filter(el -> !el.attr("abs:href").contains("/gallery"))
				.filter(el -> !el.attr("abs:href").contains("/video"))
				.filter(el -> !el.attr("abs:href").contains("/advertise"))
				.filter(el -> !el.attr("abs:href").contains("/special-supplement"))
				.filter(el -> !el.attr("abs:href").contains("/circulation"))
				.filter(el -> !el.attr("abs:href").contains("/22221"))
				.filter(el -> !el.attr("abs:href").contains("/protichinta"))
				.filter(el -> !el.attr("abs:href").contains("/kishoralo"))
				.filter(el -> !el.attr("abs:href").contains("/onnoalo"))
				.filter(el -> !el.attr("abs:href").contains("/gollachut"))
				.filter(el -> !el.attr("abs:href").contains("/shapno"))
				.filter(el -> !el.attr("abs:href").contains("/trust"))
				.filter(el -> !el.attr("abs:href").contains("/features"))
				.filter(el -> !el.attr("abs:href").contains("/holiday"))
				.filter(el -> !el.attr("abs:href").contains("/anna-alo"))
				.filter(el -> !el.attr("abs:href").contains("/naksha"))
				.filter(el -> !el.attr("abs:href").contains("/ananda"))
				.filter(el -> !el.attr("abs:href").contains("/personality"))
				.filter(el -> !el.attr("abs:href").contains("/torun"))
				.filter(el -> !el.attr("abs:href").contains("/we-are-others"))
				.filter(el -> !el.attr("abs:href").contains("/personality"))
				.filter(el -> !el.attr("abs:href").contains("/privacy"))
				.filter(el -> !el.attr("abs:href").contains("/terms"))
				.map(el -> el.attr("abs:href"))
				.collect(Collectors.toSet());
	}
	
	@Override
	protected String[] getAllExtactedLinks() {
		return linksSet.toArray(new String[linksSet.size()]);
	}

}