package com.trademaster.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Instrumento;
import com.trademaster.model.Ordem;
import com.trademaster.model.Usuario;

public class OrdemDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {
		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva uma ordem
	public void save(Ordem ordem) {

		getMongoOperations().save(ordem);
	}

	// Recupera a carteira de um usuário
	public List<Ordem> findByUser(Usuario user) {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("usuario").is(user));

		// find the saved user again.
		List<Ordem> ordens = getMongoOperations().find(searchUserQuery,
				Ordem.class);

		return ordens;

	}

	// Recupera as ordens dos demais usuários para ver se dah trade
	public List<Ordem> findByEquityAndNotUser(Usuario user, String instrumento,
			Sort sort) {

		InstrumentoDAO instDAO = new InstrumentoDAO();
		Instrumento inst = instDAO.findBySymbol(instrumento);

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("usuario").ne(user)
				.and("instrumento").is(inst));
		searchUserQuery.with(sort);

		// find the saved user again.
		List<Ordem> ordens = getMongoOperations().find(searchUserQuery,
				Ordem.class);

		return ordens;

	}
}
