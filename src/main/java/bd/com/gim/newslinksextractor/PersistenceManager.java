package bd.com.gim.newslinksextractor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * @author Tanbirul Hashan
 * @since 2020-02-22
 */
public enum PersistenceManager {
	INSTANCE;
	private EntityManagerFactory emFactory;
	private EntityManager em;

	private PersistenceManager() {
		emFactory = Persistence.createEntityManagerFactory("news-links-extractor");
	}

	public EntityManager initEntityManager() {
		em = emFactory.createEntityManager();
		return em;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}

	public void close() {
		emFactory.close();
	}
}