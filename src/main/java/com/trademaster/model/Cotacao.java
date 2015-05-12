package com.trademaster.model;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cotacoes")
public class Cotacao {

	@Id
	private String Id;

	@Field("instrumento")
	private Instrumento instrumento;

	@Field("data_pregao")
	private Calendar dataPregao;

	@Field("ultimo_preco")
	private Double ultimoPreco;

	@Field("hora_ultimo_negocio")
	private Calendar horaUltimoNegocio;

	@Field("preco_abertura")
	private Double precoAbertura;

	@Field("preco_fechamento")
	private Double precoFechamento;

	@Field("preco_maximo")
	private Double precoMaximo;

	@Field("preco_minimo")
	private Double precoMinimo;

	@Field("qtd_negocios")
	private Long qtdNegocios;

	@Field("oscilacao")
	private Double oscilacao;

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Calendar getDataPregao() {
		return dataPregao;
	}

	public void setDataPregao(Calendar dataPregao) {
		this.dataPregao = dataPregao;
	}

	public Double getUltimoPreco() {
		return ultimoPreco;
	}

	public void setUltimoPreco(Double ultimoPreco) {
		this.ultimoPreco = ultimoPreco;
	}

	public Calendar getHoraUltimoNegocio() {
		return horaUltimoNegocio;
	}

	public void setHoraUltimoNegocio(Calendar horaUltimoNegocio) {
		this.horaUltimoNegocio = horaUltimoNegocio;
	}

	public Double getPrecoAbertura() {
		return precoAbertura;
	}

	public void setPrecoAbertura(Double precoAbertura) {
		this.precoAbertura = precoAbertura;
	}

	public Double getPrecoFechamento() {
		return precoFechamento;
	}

	public void setPrecoFechamento(Double precoFechamento) {
		this.precoFechamento = precoFechamento;
	}

	public Double getPrecoMaximo() {
		return precoMaximo;
	}

	public void setPrecoMaximo(Double precoMaximo) {
		this.precoMaximo = precoMaximo;
	}

	public Double getPrecoMinimo() {
		return precoMinimo;
	}

	public void setPrecoMinimo(Double precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	public Long getQtdNegocios() {
		return qtdNegocios;
	}

	public void setQtdNegocios(Long qtdNegocios) {
		this.qtdNegocios = qtdNegocios;
	}

	public Double getOscilacao() {
		return oscilacao;
	}

	public void setOscilacao(Double oscilacao) {
		this.oscilacao = oscilacao;
	}

	
}
