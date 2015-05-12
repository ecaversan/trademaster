package com.trademaster.dao;

import org.springframework.data.mongodb.core.MongoOperations;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Trade;

public class TradeDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {
		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva um trade
	public void save(Trade trade) {

		getMongoOperations().save(trade);
	}
}
