package bd.com.gim.newslinksextractor.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
public class NewsServiceImpl implements NewsService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private EntityManager em;

	@Override
	public News insert(News ob) {
		try {
			em = PersistenceManager.INSTANCE.getEntityManager();
			em.getTransaction().begin();
			em.persist(ob);
			em.getTransaction().commit();
			return ob;
		} catch (IllegalStateException | PersistenceException | IllegalArgumentException e) {
			log.warn(String.format("\"%s\" failed to save", ob.getUrl()), e);
			return null;
		}

	}

}
