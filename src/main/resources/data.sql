CREATE USER'gim_user'@'localhost'IDENTIFIED BY'12345Gim';

CREATE DATABASE prothom_alo_news;

GRANT ALL ON prothom_alo_news.* TO 'gim_user'@'localhost' IDENTIFIED BY '12345Gim' WITH GRANT OPTION;



FLUSH PRIVILEGES;

create table prothom_alo_news.news(
   news_id INT NOT NULL AUTO_INCREMENT,
   news_title_sum VARCHAR(100) NOT NULL,
   news_category VARCHAR(40) NOT NULL,
   news_url VARCHAR(3000) NOT NULL,
   PRIMARY KEY ( news_id )
);



