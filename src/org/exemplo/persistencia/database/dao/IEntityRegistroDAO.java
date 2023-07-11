package org.exemplo.persistencia.database.dao;

import java.util.List;

import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;
import org.exemplo.persistencia.database.model.RegistroTransacao;

public interface IEntityRegistroDAO <T>{

	public void save(T t);
	public RegistroTransacao findById(Integer id);
	public List<T> findAll();
	public void update(T t);
	public void delete(T t);
	public List<RegistroTransacao> getExtrato(ContaBancaria contaExtrato, int mesExtrato, int anoExtrato);
	
}
