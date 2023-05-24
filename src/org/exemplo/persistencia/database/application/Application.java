package org.exemplo.persistencia.database.application;

import org.exemplo.persistencia.database.dao.PacienteDAO;
import org.exemplo.persistencia.database.model.Paciente;

public class Application {

	public static void main(String[] args) {
		
		PacienteDAO dao = new PacienteDAO();
		
		dao.save(new Paciente(1, "Gustavo", 1.8f, 80f));
		
	}
}
