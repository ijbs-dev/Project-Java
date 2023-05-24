package org.exemplo.persistencia.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.exemplo.persistencia.database.db.ConexaoBancoMySQL;
import org.exemplo.persistencia.database.model.Paciente;

public class PacienteDAO {

	
	public void save(Paciente p) {
		String sql = "INSERT INTO PRONTUARIO.PACIENTE VALUES (?,?,?,?);";
		
		try {
			PreparedStatement ptsm = ConexaoBancoMySQL.getConnection().prepareStatement(sql);
			ptsm.setInt(1, 0);
			ptsm.setString(2, p.getNome());
			ptsm.setFloat(3, p.getAltura());
			ptsm.setFloat(4, p.getPeso());
			ptsm.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(Paciente p) {
		
	}
	
	public void update(Paciente p) {
		
	}
	
	public Paciente findById(Integer id) {
		return null;
	}
}
