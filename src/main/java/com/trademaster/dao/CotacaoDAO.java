package com.trademaster.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Cotacao;
import com.trademaster.model.Instrumento;

public class CotacaoDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {
		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva uma cotacao
	public void save(Cotacao cotacao) {

		getMongoOperations().save(cotacao);
	}
	
	public List<Cotacao> findAll(){
		
		return getMongoOperations().findAll(Cotacao.class);
		
	}

	// Consulta a cotacao de um instrumento pelo simbolo
	public Cotacao findBySymbol(String symbol) {

		// Recupera o instrumento pelo símbolo
		InstrumentoDAO instDao = new InstrumentoDAO();
		Instrumento inst = instDao.findBySymbol(symbol);

		// query para buscar a cotacao
		Query searchQuery = new Query(Criteria.where("instrumento").is(inst));

		Cotacao cotacao = getMongoOperations().findOne(searchQuery,
				Cotacao.class);

		return cotacao;
	}

}
