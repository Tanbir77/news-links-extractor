package bd.com.gim.newslinksextractor.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import bd.com.gim.newslinksextractor.PersistenceManager;
import bd.com.gim.newslinksextractor.model.News;
import junit.framework.TestCase;

public class NewsServiceTest extends TestCase {
	static final String SAMPLE_NEWS_LINK = "https://www.prothomalo.com/topic/%E0%A6%8F%E0%A6%95%E0%A7%81%E0%A6%B6%E0%A7%87-%E0%A6%97%E0%A7%8D%E0%A6%B0%E0%A6%A8%E0%A7%8D%E0%A6%A5%E0%A6%AE%E0%A7%87%E0%A6%B2%E0%A6%BE";
	private static NewsService ns;

	public void insert() throws UnsupportedEncodingException {
		ns = new NewsServiceImpl();
		
		PersistenceManager.INSTANCE.initEntityManager();

		assertNotNull(ns.insert(getPreparedNews()));
		
		PersistenceManager.INSTANCE.getEntityManager().close();
		PersistenceManager.INSTANCE.close();

	}

	private News getPreparedNews() throws UnsupportedEncodingException {
		return new News().setCategory("topic").setTitleSum(URLDecoder.decode(
				"%E0%A6%8F%E0%A6%95%E0%A7%81%E0%A6%B6%E0%A7%87-%E0%A6%97%E0%A7%8D%E0%A6%B0%E0%A6%A8%E0%A7%8D%E0%A6%A5%E0%A6%AE%E0%A7%87%E0%A6%B2%E0%A6%BE",
				StandardCharsets.UTF_8.toString())).setUrl(SAMPLE_NEWS_LINK);
	}
}
