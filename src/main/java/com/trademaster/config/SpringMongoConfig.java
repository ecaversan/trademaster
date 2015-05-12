package com.trademaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

//import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		String openshiftMongoDbHost = System
				.getenv("OPENSHIFT_MONGODB_DB_HOST");
		int openshiftMongoDbPort = Integer.parseInt(System
				.getenv("OPENSHIFT_MONGODB_DB_PORT"));
		String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
		String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

		String env = System.getenv("OPENSHIFT_MONGODB_DB_ENVIROMENT");

		if (env.equals("DEV")) {
			username = "";
			password = "";
		}
		MongoClient mongocl = new MongoClient(openshiftMongoDbHost, openshiftMongoDbPort);
		
		UserCredentials userCredentials = new UserCredentials(username,
				password);
		String databaseName = System.getenv("OPENSHIFT_APP_NAME");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongocl,
				databaseName, userCredentials);
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
		return mongoTemplate;
	}

}