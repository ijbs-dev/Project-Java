package org.exemplo.persistencia.database.application;

import org.exemplo.persistencia.database.dao.PacienteDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoMySQL;
import org.exemplo.persistencia.database.model.Paciente;

public class Application {

	public static void main(String[] args) {
		
		PacienteDAO dao = new PacienteDAO(new ConexaoBancoMySQL());
//		Paciente p1 = new Paciente(1, "Gustavo", 1.8f, 80f);
//		Exame e1 = new Exame(1, "Exame de Sangue", "Vc tá muito bem, continue assim");
//		e1.setIdPaciente(p1.getId());
//		p1.getExames().add(e1);
//		dao.save(p1);
		
		Paciente p = dao.findById(1);
		
		System.out.println(p);
	}
}
