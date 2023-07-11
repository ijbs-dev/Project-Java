package org.exemplo.persistencia.database.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class RegistroTransacao {

	private Integer idRegistro;
	private Float valorRegistro;
	private String tipoRegistro;
	private LocalDateTime dataRegistro;
	private Integer idConta;

	
	//public RegistroTransacao(BigDecimal quantia, TipoTransacao credito, LocalDateTime localDateTime) {}


	public RegistroTransacao(Float valorRegistro, String tipoRegistro, LocalDateTime now, Integer idConta) {
		super();
		this.idRegistro = new Random().nextInt(999999999);
		this.valorRegistro = valorRegistro;
		this.tipoRegistro = tipoRegistro;
		this.dataRegistro = LocalDateTime.now();
		this.idConta = idConta;
	}

	
	public RegistroTransacao(float quantia, TipoTransacao credito, LocalDateTime now) {
		// TODO Auto-generated constructor stub
	}



	public RegistroTransacao(int i, float f, String string, Date date, int j) {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdRegistro() {
		return idRegistro;
	}


	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}


	public Float getValorRegistro() {
		return valorRegistro;
	}


	public void setValorRegistro(Float valorRegistro) {
		this.valorRegistro = valorRegistro;
	}


	public String getTipoRegistro() {
		return tipoRegistro;
	}


	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}


	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}


	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}


	public Integer getIdConta() {
		return idConta;
	}


	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}


	@Override
	public int hashCode() {
		return Objects.hash(dataRegistro, idConta, idRegistro, tipoRegistro, valorRegistro);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroTransacao other = (RegistroTransacao) obj;
		return Objects.equals(dataRegistro, other.dataRegistro) && Objects.equals(idConta, other.idConta)
				&& Objects.equals(idRegistro, other.idRegistro) && Objects.equals(tipoRegistro, other.tipoRegistro)
				&& Objects.equals(valorRegistro, other.valorRegistro);
	}


	@Override
	public String toString() {
		return "RegistroTransacao [idRegistro=" + idRegistro + ", valorRegistro=" + valorRegistro + ", tipoRegistro="
				+ tipoRegistro + ", dataRegistro=" + dataRegistro + ", idConta=" + idConta + "]";
	};
		
	
}
