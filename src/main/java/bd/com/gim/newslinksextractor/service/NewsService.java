package bd.com.gim.newslinksextractor.service;

import bd.com.gim.newslinksextractor.model.News;
/**
 * News Service interface
 * 
 * @author Tanbirul Hashan
 * @since 2020-02-22
 */
public interface NewsService{
	News insert(News ob);
	boolean isNewsExistsInDB(String url); 
}
