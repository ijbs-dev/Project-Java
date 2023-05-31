package org.exemplo.persistencia.database.application;

import org.exemplo.persistencia.database.dao.ExameDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.dao.PacienteDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoMySQL;
import org.exemplo.persistencia.database.model.Exame;
import org.exemplo.persistencia.database.model.Paciente;

public class Application {

	public static void main(String[] args) {
		
		IEntityDAO<Paciente> daoPac = new PacienteDAO(new ConexaoBancoMySQL());
		IEntityDAO<Exame> daoExa = new ExameDAO(new ConexaoBancoMySQL());
		
		
		for(Paciente p : daoPac.findAll()) {
			System.out.println(p);
		}
		
	}
}
