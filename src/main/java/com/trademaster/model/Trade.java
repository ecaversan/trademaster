package com.trademaster.model;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trades")
public class Trade {
	
	@Id
	private String Id;

	@Field("bid")
	private Ordem bid ;
	
	@Field("ask")
	private Ordem ask ;
	
	@Field("preco")
	private Double preco ;
	
	@Field("quantidade")
	private Long quantidade ;
	
	@Field("data_hora")
	private Calendar dataHora ;

	public Ordem getBid() {
		return bid;
	}

	public void setBid(Ordem bid) {
		this.bid = bid;
	}

	public Ordem getAsk() {
		return ask;
	}

	public void setAsk(Ordem ask) {
		this.ask = ask;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Calendar getDataHora() {
		return dataHora;
	}

	public void setDataHora(Calendar dataHora) {
		this.dataHora = dataHora;
	}
	
	

}
