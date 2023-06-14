package org.exemplo.persistencia.database.application;

import java.util.ArrayList;
import java.util.Iterator;

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

		Paciente pac = new Paciente();
		/*pac.setId(4);
		pac.setNome("ISMAEL JEFTE");
		pac.setAltura(1.75f);
		pac.setPeso(70.5f);
		pac.setExames(new ArrayList<>());
		*/
		
		  for(Paciente p : daoPac.findAll()) { System.out.println(p); }	  
		  

		 

		//daoPac.save(pac);
		
		/*pac.setId(1);
		daoPac.delete(pac);*/
		
		/*pac.setId(2);
		pac.setNome("Misma");
		pac.setAltura(1.55f);
		pac.setPeso(50.5f);
		pac.setExames(new ArrayList<>());
		daoPac.update(pac);
		*/
		
	}
	
}
