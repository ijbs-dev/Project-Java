package org.exemplo.persistencia.database.dao;

import java.util.List;

import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;

public interface IEntityContaDAO <T>{

	public void save(T t);
	public ContaBancaria findById(Integer id);
	public ContaBancaria findById(Cliente clienteContas);
	public List<T> findAll();
	public void update(T t);
	public void delete(T t);
	public void depositar(T t, float valor);
	public void sacar(T t, float valor);
	public void transferir(T o, T d,  float valor);
	public void imprimirExtratoConta(T e, int mes, int ano);
	public List<ContaBancaria> findByIdCliente(Cliente clienteRemovido);
	
	
}
