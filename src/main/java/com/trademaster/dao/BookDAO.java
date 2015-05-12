package com.trademaster.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.core.OrdemStatus;
import com.trademaster.model.Book;
import com.trademaster.model.BookDetail;
import com.trademaster.model.BookSide;
import com.trademaster.model.Instrumento;
import com.trademaster.model.Ordem;

public class BookDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {

		return ConnectionDBFactory.getMongoOperations();
	}

	// Recupera a carteira de um usuário
	public Book find(String equity) {

		InstrumentoDAO instDao = new InstrumentoDAO();
		Instrumento inst = instDao.findBySymbol(equity);

		Criteria criteriaStatusPendente = Criteria.where("status").is(
				OrdemStatus.PENDENTE);
		Criteria criteriaStatusParcial = Criteria.where("status").is(
				OrdemStatus.PARCIAL);
		Criteria criteriaStatusAguardando = Criteria.where("status").is(
				OrdemStatus.AGUARDANDO);

		// query to search Asks - Venda
		Query searchAsks = new Query(Criteria
				.where("instrumento")
				.is(inst)
				.and("tipo")
				.is("V")
				.orOperator(criteriaStatusParcial, criteriaStatusPendente,
						criteriaStatusAguardando));
		// query to search Bids - Compra
		Query searchBids = new Query(Criteria
				.where("instrumento")
				.is(inst)
				.and("tipo")
				.is("C")
				.orOperator(criteriaStatusParcial, criteriaStatusPendente,
						criteriaStatusAguardando));

		Sort sortAsks = new Sort(Sort.Direction.ASC, "preco");
		Sort sortBids = new Sort(Sort.Direction.DESC, "preco");

		searchAsks.with(sortAsks);
		searchBids.with(sortBids);

		// find the saved user again.
		List<Ordem> asks = getMongoOperations().find(searchAsks, Ordem.class);
		List<Ordem> bids = getMongoOperations().find(searchBids, Ordem.class);
		List<BookDetail> details = new ArrayList<BookDetail>();

		BookDetail detail ;

		if (asks.size() >= bids.size()) {
			for (int i = 0; i < bids.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(asks.get(i).getPreco());
				detail.setAskQuantidade(asks.get(i).getQuantidade());
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getQuantidade());
				details.add(detail);
			}

			if (asks.size() > bids.size()) {
				for (int i = bids.size(); i < asks.size(); i++) {
					detail = new BookDetail();
					detail.setAskPreco(asks.get(i).getPreco());
					detail.setAskQuantidade(asks.get(i).getQuantidade());
					detail.setBidPreco(0d);
					detail.setBidQuantidade(0l);
					details.add(detail);
				}
			}
		} else {
			for (int i = 0; i < asks.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(asks.get(i).getPreco());
				detail.setAskQuantidade(asks.get(i).getQuantidade());
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getQuantidade());
				details.add(detail);
			}

			for (int i = asks.size(); i < bids.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(0d);
				detail.setAskQuantidade(0l);
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getQuantidade());
				details.add(detail);
			}

		}

		Book book = new Book();
		book.setDetail(details);
		book.setUltimaAlteracao(Calendar.getInstance());

		return book;

	}
	
	// Recupera a carteira de um usuário
	public Book findByInstrumentAgg(String equity) {

		InstrumentoDAO instDao = new InstrumentoDAO();
		Instrumento inst = instDao.findBySymbol(equity);

		Criteria criteriaStatusPendente = Criteria.where("status").is(
				OrdemStatus.PENDENTE);
		Criteria criteriaStatusParcial = Criteria.where("status").is(
				OrdemStatus.PARCIAL);
		Criteria criteriaStatusAguardando = Criteria.where("status").is(
				OrdemStatus.AGUARDANDO);
		
		Criteria criteriaAsks = Criteria
				.where("instrumento")
				.is(inst)
				.and("tipo")
				.is("V")
				.orOperator(criteriaStatusParcial, criteriaStatusPendente,
						criteriaStatusAguardando);

		
		Criteria criteriaBids = Criteria
				.where("instrumento")
				.is(inst)
				.and("tipo")
				.is("C")
				.orOperator(criteriaStatusParcial, criteriaStatusPendente,
						criteriaStatusAguardando);

		Sort sortAsks = new Sort(Sort.Direction.ASC, "preco");
		Sort sortBids = new Sort(Sort.Direction.DESC, "preco");
		
		Aggregation aggAsks = newAggregation(
				match(criteriaAsks), 
				group("preco").sum("quantidade").as("total"),
				project("total").and("preco").previousOperation(),
				sort(sortAsks)
			);

		// Convert the aggregation result into a List
		AggregationResults<BookSide> groupResults = getMongoOperations()
				.aggregate(aggAsks, Ordem.class, BookSide.class);
		List<BookSide> asks = groupResults.getMappedResults();

		
		Aggregation aggBids = newAggregation(
				match(criteriaBids), 
				group("preco").sum("quantidade").as("total"),
				project("total").and("preco").previousOperation(),
				sort(sortBids)
			);

		// Convert the aggregation result into a List
		AggregationResults<BookSide> groupResultsBids = getMongoOperations()
				.aggregate(aggBids, Ordem.class, BookSide.class);
		List<BookSide> bids = groupResultsBids.getMappedResults();

		
		// find the saved user again.
		//List<Ordem> asks = getMongoOperations().find(searchAsks, Ordem.class);
		//List<Ordem> bids = getMongoOperations().find(searchBids, Ordem.class);
		List<BookDetail> details = new ArrayList<BookDetail>();

		BookDetail detail ;

		if (asks.size() >= bids.size()) {
			for (int i = 0; i < bids.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(asks.get(i).getPreco());
				detail.setAskQuantidade(asks.get(i).getTotal());
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getTotal());
				details.add(detail);
			}

			if (asks.size() > bids.size()) {
				for (int i = bids.size(); i < asks.size(); i++) {
					detail = new BookDetail();
					detail.setAskPreco(asks.get(i).getPreco());
					detail.setAskQuantidade(asks.get(i).getTotal());
					detail.setBidPreco(0d);
					detail.setBidQuantidade(0l);
					details.add(detail);
				}
			}
		} else {
			for (int i = 0; i < asks.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(asks.get(i).getPreco());
				detail.setAskQuantidade(asks.get(i).getTotal());
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getTotal());
				details.add(detail);
			}

			for (int i = asks.size(); i < bids.size(); i++) {
				detail = new BookDetail();
				detail.setAskPreco(0d);
				detail.setAskQuantidade(0l);
				detail.setBidPreco(bids.get(i).getPreco());
				detail.setBidQuantidade(bids.get(i).getTotal());
				details.add(detail);
			}

		}

		Book book = new Book();
		book.setDetail(details);
		book.setUltimaAlteracao(Calendar.getInstance());

		return book;

	}
}
