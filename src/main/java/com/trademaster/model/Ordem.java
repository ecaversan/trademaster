package com.trademaster.model;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.trademaster.core.OrdemStatus;

@Document(collection = "ordens")
public class Ordem {

	@Id
	private String Id;

	@Field("usuario")
	private Usuario usuario;

	@Field("instrumento")
	private Instrumento instrumento;

	@Field("tipo")
	private String tipo;

	@Field("quantidade")
	private Long quantidade;

	@Field("preco")
	private Double preco;

	@Field("status")
	private OrdemStatus status;

	@Field("data_inclusao")
	private Calendar dataInclusao;

	@Field("data_vencimento")
	private Calendar dataVencimento;

	@Field("data_execucao")
	private Calendar dataExecucao;

	@Field("preco_executado")
	private Double precoExecutado;

	@Field("day_trade")
	private String dayTrade;

	@Field("corretagem")
	private Double corretagem;

	@Field("emolumentos")
	private Double emolumentos;

	@Field("valor_operacao")
	private Double valorOperacao;
	
	@Field("usuario_string")
	private String usuarioString;
	
	@Field("instrumento_string")
	private String instrumentoString;

	@Field("vencimento_string")
	private String vencimentoString;

	public String getVencimentoString() {
		return vencimentoString;
	}

	public void setVencimentoString(String vencimentoString) {
		this.vencimentoString = vencimentoString;
	}

	public String getInstrumentoString() {
		return instrumentoString;
	}

	public void setInstrumentoString(String instrumentoString) {
		this.instrumentoString = instrumentoString;
	}

	public String getUsuarioString() {
		return usuarioString;
	}

	public void setUsuarioString(String usuarioString) {
		this.usuarioString = usuarioString;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public OrdemStatus getStatus() {
		return status;
	}

	public void setStatus(OrdemStatus status) {
		this.status = status;
	}

	public Calendar getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Calendar dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Calendar getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Calendar dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Calendar getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(Calendar dataExecucao) {
		this.dataExecucao = dataExecucao;
	}

	public Double getPrecoExecutado() {
		return precoExecutado;
	}

	public void setPrecoExecutado(Double precoExecutado) {
		this.precoExecutado = precoExecutado;
	}

	public String getDayTrade() {
		return dayTrade;
	}

	public void setDayTrade(String dayTrade) {
		this.dayTrade = dayTrade;
	}

	public Double getCorretagem() {
		return corretagem;
	}

	public void setCorretagem(Double corretagem) {
		this.corretagem = corretagem;
	}

	public Double getEmolumentos() {
		return emolumentos;
	}

	public void setEmolumentos(Double emolumentos) {
		this.emolumentos = emolumentos;
	}

	public Double getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(Double valorOperacao) {
		this.valorOperacao = valorOperacao;
	}

	
}
