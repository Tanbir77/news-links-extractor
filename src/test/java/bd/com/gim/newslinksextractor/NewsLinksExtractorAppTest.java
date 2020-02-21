package bd.com.gim.newslinksextractor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import junit.framework.TestCase;

public class NewsLinksExtractorAppTest extends TestCase {
	
	static final String URL = "https://www.prothomalo.com/";
	static final String SAMPLE_NEWS_LINK = "https://www.prothomalo.com/topic/%E0%A6%8F%E0%A6%95%E0%A7%81%E0%A6%B6%E0%A7%87-%E0%A6%97%E0%A7%8D%E0%A6%B0%E0%A6%A8%E0%A7%8D%E0%A6%A5%E0%A6%AE%E0%A7%87%E0%A6%B2%E0%A6%BE";
	Set<String> linksSet = new LinkedHashSet<>();

	public void FetchtUrlsTest() throws IOException, InterruptedException {
		print("Fetching %s...", URL);
		
		Document doc = Jsoup.connect(URL).get();
		Elements hrefEls = doc.select("a[abs:href~=(https|http)://www.prothomalo.com/]");
		
		print("\nTotal Links: (%d)", hrefEls.size());
		
		assertEquals(201, hrefEls.size());
	}

	public void LinksFilterTest() throws IOException {
		print("Fetching %s...", URL);

		Document doc = Jsoup.connect(URL).get();
		Elements hrefEls = doc.select("a[abs:href~=(https|http)://www.prothomalo.com/]");

		print("\nTotal Links: (%d)", hrefEls.size());
		print("\n All links: \n");

		hrefEls.stream().forEach(el -> print(" * <%s>", el.attr("abs:href")));

		linksSet.addAll(
				hrefEls.stream()
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
				.filter(el -> !el.attr("abs:href").contains("/terms")).map(el -> el.attr("abs:href"))
				.collect(Collectors.toSet()));

		print("\nTotal unique links: (%d)", linksSet.size());
		print("Unique links: (%d)\n", linksSet.size());
		linksSet.forEach(el -> print(" * <%s>", el));
		
		assertTrue(hrefEls.size() > linksSet.size());
	}

	public void urlDecodingTest() throws UnsupportedEncodingException {
		System.out.println(URLDecoder.decode(SAMPLE_NEWS_LINK, StandardCharsets.UTF_8.toString()));
	}

	public void newsLinkValidityTest() {
		String[] parts = SAMPLE_NEWS_LINK.split("/");
		assertTrue(parts.length > 4 && Pattern.compile("[^#A-Za-z0-9-]").matcher(parts[parts.length - 1]).find());
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

}
