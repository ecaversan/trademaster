package com.trademaster.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Instrumento;

public class InstrumentoDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {
		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva um instrumento
	public void save(Instrumento inst) {

		if (inst.getSituation() == null) {
			inst.setSituation("A");
		}

		if (inst.getMaturity() == null) {
			inst.setMaturity("");
		}

		getMongoOperations().save(inst);
	}

	// Lista todos os instrumentos cadastrados
	public List<Instrumento> findAll() {

		// find the saved user again.
		List<Instrumento> insts = getMongoOperations().findAll(
				Instrumento.class);
		return insts;

	}

	// Consulta um instrumento pelo símbolo
	public Instrumento findBySymbol(String symbol) {

		Instrumento inst = new Instrumento();

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("symbol").is(symbol));

		// find the saved user again.
		inst = getMongoOperations().findOne(searchUserQuery, Instrumento.class);

		return inst;
	}

}
