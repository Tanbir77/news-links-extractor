
CREATE USER IF NOT EXISTS 'gim_user77'@'localhost'IDENTIFIED BY'12345Gim';



CREATE DATABASE IF NOT EXISTS prothom_alo_news;

GRANT ALL ON prothom_alo_news.* TO 'gim_user77'@'localhost' IDENTIFIED BY '12345Gim' WITH GRANT OPTION;



FLUSH PRIVILEGES;

DROP TABLE IF EXISTS prothom_alo_news.news;

create table prothom_alo_news.news(
   news_id INT NOT NULL AUTO_INCREMENT,
   news_title_sum VARCHAR(200),
   news_category VARCHAR(30) NOT NULL,
   news_url VARCHAR(2000) NOT NULL UNIQUE,
   PRIMARY KEY ( news_id )
);

ALTER TABLE  prothom_alo_news.news MODIFY news_title_sum VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

SET CHARACTER SET utf8;
SET SESSION collation_connection ='utf8_unicode_ci';





