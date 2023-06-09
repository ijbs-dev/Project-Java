package org.exemplo.persistencia.database.dao;

import java.sql.Connection;
import java.util.List;

import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;
import org.exemplo.persistencia.database.model.RegistroTransacao;

public interface IEntityDAO <T>{

	public void save(T t);
	public Cliente findById(int idCliente);
	//public Cliente findByCpf(String cpf);
	public List<T> findAll();
	public void update(T t);
	public void delete(T t);
	public double balancoEntreContas();
	
}
