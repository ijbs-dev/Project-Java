package org.exemplo.persistencia.database.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Paciente {

	private Integer id;
	private String nome;
	private Float altura;
	private Float peso;
	private List<Exame> exames;
	
	public Paciente() {};
	
	public Paciente(Integer id, String nome, Float altura, Float peso) {
		super();
		this.id = id;
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
		exames = new ArrayList<>();
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

	public Float getAltura() {
		return altura;
	}

	public void setAltura(Float altura) {
		this.altura = altura;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nome=" + nome + ", altura=" + altura + ", peso=" + peso + ", exames=" + exames
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public List<Exame> getExames() {
		return exames;
	}
	
	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(id, other.id);
	}
	
	
	public Float calcularIMC() {
		return (float) (peso / Math.pow(altura, 2));
	}
	
	public Exame localeById(Integer id) {
		Exame e = new Exame();
		e.setId(id);
		
		if(exames.contains(e)) {
			int index = exames.indexOf(e);
			return exames.get(index);
		}else
			return null;
	}
}
