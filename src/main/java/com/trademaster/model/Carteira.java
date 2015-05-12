package com.trademaster.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;

@SuppressWarnings("unused")
@Document(collection = "carteiras")
public class Carteira {

	@Id
	private String id;

	@Field("instrumento")
	private Instrumento instrumento;

	@Field("quantidade")
	private Long quantidade;

	@Field("preco_medio")
	private Double preco_medio;

	@Field("preco_atual")
	private Double preco_atual;

	@Field("valor_total")
	private Double valor_total;

	@Field("ganho_perda")
	private Double ganho_perda;

	@Field("percentual")
	private Double percentual;

	@Field("usuario")
	private Usuario usuario;

	@Field("trades")
	private List<Trade> trades;

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco_medio() {
		return preco_medio;
	}

	public void setPreco_medio(Double preco_medio) {
		this.preco_medio = preco_medio;
	}

	public Double getPreco_atual() {
		return preco_atual;
	}

	public void setPreco_atual(Double preco_atual) {
		this.preco_atual = preco_atual;
	}

	public Double getValor_total() {
		return this.preco_atual * this.quantidade;
	}

	public Double getGanho_perda() {
		return (this.preco_atual - this.preco_medio) * this.quantidade;
	}

	public Double getPercentual() {
		return ((this.preco_atual * 100) / this.preco_medio) - 100;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public void setGanho_perda(Double ganho_perda) {
		this.ganho_perda = ganho_perda;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}


}
