package com.trademaster.model;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "books")
public class Book {

	@Id
	private String Id;

	@Field("detail")
	private List<BookDetail> detail ;

	@Field("ultima_alteracao")
	private Calendar ultimaAlteracao ;

	public List<BookDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<BookDetail> detail) {
		this.detail = detail;
	}

	public Calendar getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Calendar ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	
}
