package com.trademaster.dao;

//imports as static
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;


import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Carteira;
import com.trademaster.model.Instrumento;
import com.trademaster.model.InstrumentoCount;
import com.trademaster.model.Trade;
import com.trademaster.model.Usuario;

public class CarteiraDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {

		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva uma carteira
	public void save(Carteira cart) {
		cart = summarize(cart);
		getMongoOperations().save(cart);
	}

	// Recupera a carteira de um usuário
	public List<Carteira> findByUser(Usuario user) {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("usuario").is(user));

		// find the saved user again.
		List<Carteira> carts = getMongoOperations().find(searchUserQuery,
				Carteira.class);
		return carts;

	}

	// Deleta a carteira de um usuário
	public void deleteByUser(Usuario user) {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("usuario").is(user));

		// find the saved user again.
		getMongoOperations().findAndRemove(searchUserQuery, Carteira.class);

	}

	// Recupera a carteira de um usuário
	public Carteira findByInstrumentAndUser(Instrumento inst, Usuario user) {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("usuario").is(user)
				.and("instrumento").is(inst));

		// find the saved user again.
		Carteira cart = getMongoOperations().findOne(searchUserQuery,
				Carteira.class);

		return cart;

	}

	public Carteira summarize(Carteira carteira) {

		// Calcula preço médio
		Double precoMedio = carteira.getPreco_atual();
		Long quantidade = carteira.getQuantidade();
		Double percentual = 0d;

		List<Trade> trades = carteira.getTrades();
		if (!trades.isEmpty()) {
			quantidade = 0l;
			precoMedio = 0d;
			for (Trade trade : trades) {
				// Verifica se é Bid ou Ask para atualizar a quantidade
				if (trade.getAsk().getUsuario().toString().equals(carteira.getUsuario().toString())) {
					quantidade -= trade.getQuantidade();
				} else {
					quantidade += trade.getQuantidade();
				}
				precoMedio += trade.getPreco();
			}

			precoMedio = precoMedio / trades.size();
			percentual = ((carteira.getPreco_atual() * 100) / precoMedio) - 100;
		}

		carteira.setPreco_medio(precoMedio);
		carteira.setQuantidade(quantidade);
		carteira.setPercentual(percentual);
		carteira.setGanho_perda((carteira.getPreco_atual() - precoMedio)
				* quantidade);
		carteira.setValor_total(carteira.getPreco_atual() * quantidade);

		return carteira;

	}

	
	public List<InstrumentoCount> findByUserGroupByInst(Usuario user) {

		Aggregation agg = newAggregation(
				match(Criteria.where("usuario").is(user)), 
				group("instrumento").sum("quantidade").as("total"),
				project("total").and("instrumento").previousOperation(),
				sort(Sort.Direction.DESC, "total")
			);

		// Convert the aggregation result into a List
		AggregationResults<InstrumentoCount> groupResults = getMongoOperations()
				.aggregate(agg, Carteira.class, InstrumentoCount.class);
		List<InstrumentoCount> result = groupResults.getMappedResults();

		return result;
	}

}
