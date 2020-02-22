package bd.com.gim.newslinksextractor.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bd.com.gim.newslinksextractor.PersistenceManager;
import bd.com.gim.newslinksextractor.model.News;
/**
 * News Service Implementation class
 * 
 * @author Tanbirul Hashan
 * @since 2020-02-22
 */
public class NewsServiceImpl implements NewsService{
	private final Logger log = LoggerFactory.getLogger(getClass());
	private static EntityManager em;
	
	static {
		em = PersistenceManager.INSTANCE.getEntityManager();
	}
	@Override
	public News insert(News ob) {
		try {
			em.getTransaction().begin();
			em.persist(ob);
			em.getTransaction().commit();
			return ob;
		} catch (IllegalStateException | EntityExistsException | IllegalArgumentException
				| TransactionRequiredException e) {
			log.warn(String.format("\"%s\" failed to save", e));
			return null;
		}

	}

}
