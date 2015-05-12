package com.trademaster.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
 
@Document(collection = "usuarios")
public class Usuario {
 
	@Id
	private String id;
	
	@Field("name")
	String username;
	
	@Field("pwd")
	String password;
 
	//getter, setter, toString, Constructors
	public Usuario(@Value("#root.name") String userName, @Value("#root.pwd") String pass) {
		// TODO Auto-generated constructor stub
		this.username = userName ;
		this.password = pass ;
	}
	
	@Override
	public String toString(){
		return this.id + "-" + this.getUsername() + "-" + this.getPassword();
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}