# news-links-extractor
**To Run the application on your local machine**

Execute following commands

1. ```git clone https://github.com/Tanbir77/news-links-extractor.git```
2. ```cd news-links-extractor```
3. ```mysql -u root -p  < src/main/resources/db_script.sql```
4. ```./mvnw clean compile```
5. ```./mvnw exec:java -Dexec.mainClass="bd.com.gim.newslinksextractor.NewsLinksExtractorApp"```

