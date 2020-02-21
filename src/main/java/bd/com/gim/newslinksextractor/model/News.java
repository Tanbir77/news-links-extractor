package bd.com.gim.newslinksextractor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * News [dbo.news] model class
 * @author Tanbirul Hashan
 * @since 2020-02-22
 */
@Entity
@Table(name = "news")
public class News {
  @Id
  @GeneratedValue
  @Column(name="news_id", nullable=false)
  private Integer id;
  
  @Column(name="news_title_sum", nullable=false)
  private String titleSum;
  
  @Column(name="news_category", nullable=false)
  private String category;
  
  @Column(name="news_url", nullable=false)
  private String url;
 
  /**
   * @param id the id to set
   */
  public News setId(Integer id) {
    this.id = id;
    return this;
  }
  
  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }
  
  /**
   * @param title the title summary to set
   */
  public News setTitleSum(String title) {
    this.titleSum = title;
    return this;
  }
  
  /**
   * @return the title summary
   */
  public String getTitleSum() {
    return this.titleSum;
  }
  
  /**
   * @param category the category to set
   */
  public News setCategory(String category) {
    this.category = category;
    return this;
  }
  
  /**
   * @return the category
   */
  public String getCategory() {
    return this.category;
  }
  
  /**
   * @param url the url to set
   */
  public News setUrl(String url) {
    this.url = url;
    return this;
  }
  
  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }
}