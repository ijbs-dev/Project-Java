package org.exemplo.persistencia.database.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {

	//private static final long serialVersionUID = 1L;
	private Integer idCliente;
	private String cpfCliente;
	private String nomeCliente;
	private List<ContaBancaria> contas;
	
	public Cliente() {};
	
	public Cliente(Integer idCliente, String cpfCliente, String nomeCliente) {
		super();
		this.idCliente = idCliente;
		this.cpfCliente = cpfCliente;
		this.nomeCliente = nomeCliente;
		contas = new ArrayList<>();
	}

	public List<ContaBancaria> getContas() {
        if (contas == null) {
            contas = new ArrayList<>(); // Inicializar a lista de contas se for nula
        }
        return contas;
    }
	
	
	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	

	public void setContas(List<ContaBancaria> contas) {
		this.contas = contas;
	}

		
	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(idCliente, other.idCliente);
	}

	
	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", cpfCliente=" + cpfCliente + ", nomeCliente=" + nomeCliente
				+ ", contas=" + contas + "]";
	}

	//===========================================================================================================
	
	public ContaBancaria localeById(Integer id) {
		ContaBancaria c = new ContaBancaria(id, id, null, id);
		c.setIdConta(id);
		
		if(contas.contains(c)) {
			int index = contas.indexOf(c);
			return contas.get(index);
		}else
			return null;
	}
	
	public void adicionarConta(ContaBancaria c) {

		if (contas.contains(c)) {
			System.out.print("A conta jah estah associada a este cliente.");
		} else {
			this.contas.add(c);
			//PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
			System.out.print("Conta adicionada com sucesso!");
		}
	}

	public void removerConta(ContaBancaria c) {

		if (contas.contains(c)) {
			this.contas.remove(c);
			//PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
			System.out.print("Conta removida com sucesso!");
		} else {
			System.out.print("A conta nao esta associada a este cliente.");
		}
	}

	public ContaBancaria localizarContaNumero(int numero) {

		for (int i = 0; i < contas.size(); i++) {
			ContaBancaria c = contas.get(i);

			if (c.getNumeroConta() == numero) {
				System.out.print("Conta encontrada!");
				return c;
			}
		}
		System.out.print("Conta nao encontrada.");
		return null;
	}

	public double balancoEntreContas() {

		double ValorSaldo = 0.0;
		for (int i = 0; i < contas.size(); i++) {
			ContaBancaria c = contas.get(i);
			ValorSaldo += c.getSaldoConta();
		}

		System.out.print("Balanco entre contas: RS" + ValorSaldo);
		return ValorSaldo;
	}

	public ContaBancaria findById(int idContaRemetente) {
		// TODO Auto-generated method stub
		return null;
	}

	
}