package bd.com.gim.newslinksextractor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.persistence.EntityManager;

import bd.com.gim.newslinksextractor.model.News;
import junit.framework.TestCase;

public class PersistenceManagerTest extends TestCase {
	
	static final String URL = "https://www.prothomalo.com/";
	static final String SAMPLE_NEWS_LINK = "https://www.prothomalo.com/topic/%E0%A6%8F%E0%A6%95%E0%A7%81%E0%A6%B6%E0%A7%87-%E0%A6%97%E0%A7%8D%E0%A6%B0%E0%A6%A8%E0%A7%8D%E0%A6%A5%E0%A6%AE%E0%A7%87%E0%A6%B2%E0%A6%BE";
	public void DbInsertionWithEntityManagerTest() throws UnsupportedEncodingException {
		News news = new News();
	    news.setCategory("topic")
	    .setTitleSum(
	    		URLDecoder
	    		.decode("%E0%A6%8F%E0%A6%95%E0%A7%81%E0%A6%B6%E0%A7%87-%E0%A6%97%E0%A7%8D%E0%A6%B0%E0%A6%A8%E0%A7%8D%E0%A6%A5%E0%A6%AE%E0%A7%87%E0%A6%B2%E0%A6%BE",
	    				StandardCharsets.UTF_8.toString()))
	    .setUrl(SAMPLE_NEWS_LINK);
		
		 EntityManager em = PersistenceManager.INSTANCE.initEntityManager();
		 
		 em.getTransaction().begin();
		 em.persist(news);
		 em.getTransaction().commit();
		 
		 em.getTransaction().begin();
		 boolean isExist = em.contains(news);
		 em.getTransaction().commit();
		 
		 assertTrue(isExist);
		 
		 em.close();
		 PersistenceManager.INSTANCE.close();
	}

}
