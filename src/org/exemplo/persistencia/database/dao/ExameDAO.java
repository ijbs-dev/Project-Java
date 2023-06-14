package org.exemplo.persistencia.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Exame;
import org.exemplo.persistencia.database.model.Paciente;

public class ExameDAO implements IEntityDAO<Exame> {

	private IConnection conn;

	public ExameDAO(IConnection conn) {
		this.conn = conn;
	}

	@Override
	public void save(Exame t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO JAVAPROJECT.EXAME VALUES (?,?,?,?);";

		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
			ptsm.setInt(1, 0);
			ptsm.setString(2, t.getNome());
			ptsm.setString(3, t.getDescricao());
			ptsm.setInt(4, t.getIdPaciente());
			ptsm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Exame findById(Integer id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM EXAME WHERE ID = ?;";
		ResultSet rs;

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				return new Exame(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
						rs.getInt("idpaciente"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void update(Exame t) {
		// TODO Auto-generated method stub
		
		Exame temp = findById(t.getId());
		
		if(temp != null) {
			temp.setNome(t.getNome());
			temp.setDescricao(t.getDescricao());
			temp.setIdPaciente(t.getIdPaciente());
		}else
			temp = t;
		
		String sql = "UPDATE EXAME SET NOME = ?, DESCRICAO = ?, IDPACIENTE = ? WHERE ID = ?;";

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setString(1, temp.getNome());
			pstm.setString(2, temp.getDescricao());
			pstm.setInt(3, temp.getIdPaciente());
			pstm.setInt(4, t.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Exame t) {
		// TODO Auto-generated method stub
		String sql = "delete from javaproject.exame where id = ?;";

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, t.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Exame> findAll() {
		List<Exame> lista = new ArrayList<>();
		String sql = "SELECT * FROM JAVAPROJECT.EXAME;";
		ResultSet rs;
		
		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				lista.add(new Exame(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("idpaciente")));
			}
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
