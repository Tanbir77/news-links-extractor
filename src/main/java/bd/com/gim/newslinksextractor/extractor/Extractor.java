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
 * News Links extractor class. Targeted news site is <a href="https://www.prothomalo.com/">prothom alo</a>

 * @author Tanbirul Hashan
 * @since 2020-02-21
 */
public class Extractor extends AbstractExtractor{
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String URL = "https://www.prothomalo.com/";
	private Set<String> linksSet = new LinkedHashSet<>();

	public void ExtractUrls(){
		startSiteUrlsExtracting(URL);
	}
	
	@Override
	public void extractUrlsFromSite(String siteUrl) {
		collectUrls(siteUrl);
		
		String[] links = linksSet.toArray(new String[linksSet.size()]);
		
		for (int i = 0; i < links.length; i++) {
			if (isNewsLink(links[i]))
				saveLink(links[i]);
			else {
				collectUrls(links[i]);
				links = linksSet.toArray(new String[linksSet.size()]);
			}
		}

	}
	
	private void collectUrls(String url){
		log.info(getFormattedMsg("Fetching \"%s\" news links", url));
		
		Elements hrefEls = new Elements();
		
		try{
			Document doc = Jsoup.connect(url).timeout(60 * 1000).execute().parse();
			hrefEls = doc.select("a[abs:href~=(https|http)://www.prothomalo.com/]");
			
			/* Sleeping to avoid frequent request which may lead to be blacklisted the ip */
			Thread.sleep(5000l);
		} catch (IOException | InterruptedException e) {
			
		}

		linksSet.addAll(hrefEls.stream()
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
				.collect(Collectors.toSet()));
	}

}