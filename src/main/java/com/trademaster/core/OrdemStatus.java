package com.trademaster.core;

public enum OrdemStatus {
	PENDENTE("P"), EXECUTADA("E"), PARCIAL("R"), DELETADA("D"), AGUARDANDO("A");
	
	private String statusCode ;
	
	private OrdemStatus(String s){
		statusCode = s ;
	}
	
	public String getStatusCode(){
		return statusCode ;
	}

}
