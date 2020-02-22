# news-links-extractor
**Run the application on your local machine**

1. Clone the repository
2. ```cd news-links-extractor```
3. ```mysql -u root -p  < src/main/resources/db_script.sql```
4. ```./mvnw clean compile```
5. ```./mvnw exec:java -Dexec.mainClass="bd.com.gim.newslinksextractor.NewsLinksExtractorApp"```

