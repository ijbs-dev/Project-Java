package org.exemplo.persistencia.database.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ContaBancaria {

//	private static final long serialVersionUID = 1L;
	private Integer idConta;
	private Integer numeroConta;
	private float saldoConta;
	private LocalDateTime dataAbertConta;
	private boolean statusConta;
	private Integer idCliente;
	private List<RegistroTransacao> transacoes;

	public ContaBancaria(Integer idConta, Integer idCliente, List<RegistroTransacao> transacoes, float saldoConta) {
		this.idConta = idConta;
		this.idCliente = idCliente;
		this.numeroConta = new Random().nextInt(999999999);
		this.saldoConta = saldoConta;
		//saldoConta.setScale(4, RoundingMode.HALF_UP);
		this.dataAbertConta = LocalDateTime.now();
		this.statusConta = true;
		transacoes = new ArrayList<>();
	}

	public ContaBancaria() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdConta() {
		return idConta;
	}

	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public float getSaldoConta() {
		return saldoConta;
	}

	public void setSaldoConta(float saldoConta) {
		this.saldoConta = saldoConta;
	}

	public LocalDateTime getDataAbertConta() {
		return dataAbertConta;
	}

	public void setDataAbertConta(LocalDateTime dataAbertConta) {
		this.dataAbertConta = dataAbertConta;
	}

	public boolean isStatusConta() {
		return statusConta;
	}

	public void setStatusConta(boolean statusConta) {
		this.statusConta = statusConta;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public List<RegistroTransacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<RegistroTransacao> transacoes) {
		this.transacoes = transacoes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaBancaria other = (ContaBancaria) obj;
		return Objects.equals(idConta, other.idConta);
	}

	@Override
	public String toString() {
		return "ContaBancaria [idConta=" + idConta + ", numeroConta=" + numeroConta + ", saldoConta=" + saldoConta
				+ ", dataAbertConta=" + dataAbertConta + ", statusConta=" + statusConta + ", transacoes=" + transacoes
				+ "]";
	}


	public boolean sacar(float valorSaque) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean transferir(ContaBancaria contaDestinatario, double valorTransferencia) {
		// TODO Auto-generated method stub
		return false;
	}

	public void depositar(float valorDeposito) {
		// TODO Auto-generated method stub
		
	}

	public ContaBancaria localeById(int idContaDestinatario) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
