package com.trademaster.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trademaster.core.ConnectionDBFactory;
import com.trademaster.model.Usuario;

public class UserDAO {

	// Cria conexão com o banco
	private MongoOperations getMongoOperations() {
		return ConnectionDBFactory.getMongoOperations();
	}

	// Salva um usuário
	public void save(Usuario user) {
		getMongoOperations().save(user);
	}

	
	// Recupera um usuário pelo nome
	public Usuario findByUserName(String username) {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("username").is(username));

		// find the saved user again.
		Usuario savedUser = getMongoOperations().findOne(searchUserQuery, Usuario.class);
		return savedUser;

	}
	
	//Recupera todos usuário cadastrados
	public List<Usuario> findAll() {
		List<Usuario> usuarios = getMongoOperations().findAll(Usuario.class);
		return usuarios;
		
	}
}
