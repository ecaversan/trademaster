package com.trademaster.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.trademaster.config.SpringMongoConfig;

public final class ConnectionDBFactory {
	
	// Cria conexão com o banco
		public static final MongoOperations getMongoOperations() {
			// For Annotation
			ApplicationContext ctx = new AnnotationConfigApplicationContext(
					SpringMongoConfig.class);
			MongoOperations mongoOperation = (MongoOperations) ctx
					.getBean("mongoTemplate");
			return mongoOperation;
		}

}
