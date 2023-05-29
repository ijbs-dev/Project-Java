package org.exemplo.persistencia.database.model;

import java.util.Objects;

public class Exame {

	private Integer id;
	private String nome;
	private String descricao;
	private Integer idPaciente;
	
	public Exame(Integer id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public Exame(Integer id, String nome, String descricao, Integer idPaciente) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.idPaciente = idPaciente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public Integer getIdPaciente() {
		return idPaciente;
	}
	
	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exame other = (Exame) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Exame [id=" + id + ", nome=" + nome + ", descricao=" + descricao + "]";
	}
	
	
	
	
}
