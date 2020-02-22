package bd.com.gim.newslinksextractor;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import bd.com.gim.newslinksextractor.extractor.Extractor;

public class NewsLinksExtractorApp {
	public static void main(String[] args) throws IOException {
		String log4jConfPath = "src/main/resources/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		PersistenceManager.INSTANCE.initEntityManager();
		
		new Extractor().ExtractUrls();
		
		PersistenceManager.INSTANCE.getEntityManager().close();
		PersistenceManager.INSTANCE.close();
	}
}