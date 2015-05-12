package com.trademaster.model;

public class BookDetail {

	private Instrumento instrumento;
	private Long bidQuantidade;
	private Double bidPreco;
	private Long askQuantidade;
	private Double askPreco;

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Long getBidQuantidade() {
		return bidQuantidade;
	}

	public void setBidQuantidade(Long bidQuantidade) {
		this.bidQuantidade = bidQuantidade;
	}

	public Double getBidPreco() {
		return bidPreco;
	}

	public void setBidPreco(Double bidPreco) {
		this.bidPreco = bidPreco;
	}

	public Long getAskQuantidade() {
		return askQuantidade;
	}

	public void setAskQuantidade(Long askQuantidade) {
		this.askQuantidade = askQuantidade;
	}

	public Double getAskPreco() {
		return askPreco;
	}

	public void setAskPreco(Double askPreco) {
		this.askPreco = askPreco;
	}

}
