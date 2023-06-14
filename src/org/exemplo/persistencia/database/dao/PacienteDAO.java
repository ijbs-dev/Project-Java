package org.exemplo.persistencia.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Exame;
import org.exemplo.persistencia.database.model.Paciente;

public class PacienteDAO implements IEntityDAO<Paciente>{

	private IConnection conn;
	private IEntityDAO<Exame> exameDAO;
	
	public PacienteDAO(IConnection conn) {
		this.conn = conn;
		exameDAO = new ExameDAO(conn);
	}
	
	public void save(Paciente p) {
		String sql = "INSERT INTO javaproject.PACIENTE VALUES (?,?,?,?);";
		
		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
			ptsm.setInt(1, p.getId());
			ptsm.setString(2, p.getNome());
			ptsm.setFloat(3, p.getAltura());
			ptsm.setFloat(4, p.getPeso());
			ptsm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		for(Exame e : p.getExames()) {
			exameDAO.save(e);
		}
		
		conn.closeConnection();
	}
	
	public void delete(Paciente p) {
		String sql = "delete from javaproject.paciente where id = ?;";
		
		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, p.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void update(Paciente p) {
		
		Paciente temp = findById(p.getId());
		temp.setNome(p.getNome());
		temp.setAltura(p.getAltura());
		temp.setPeso(p.getPeso());
		temp.setExames(p.getExames());
		
		String sql = "UPDATE PACIENTE SET ID = ?, NOME = ?, ALTURA = ?, PESO = ? WHERE ID = ?;";
		try 
		{
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, temp.getId());
			pstm.setString(2, temp.getNome());
			pstm.setFloat(3, temp.getAltura());
			pstm.setFloat(4, temp.getPeso());
			pstm.setInt(5, p.getId());
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		for(Exame e : p.getExames()) {
			if(exameDAO.findById(e.getId()) != null)
				exameDAO.update(e);
			else
				exameDAO.save(e);
		}
	}
	
	public Paciente findById(Integer id) {
		String sql1 = "SELECT * FROM PACIENTE WHERE ID = ?;";
		String sql2 = "SELECT * FROM EXAME E WHERE E.IDPACIENTE = ?;";
		ResultSet rs;
		Paciente p = new Paciente();
		List<Exame> exames = new ArrayList<>();
		
		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql1);
			ptsm.setInt(1, id);
			rs = ptsm.executeQuery();
			
			while(rs.next()) {
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setAltura(rs.getFloat(3));
				p.setPeso(rs.getFloat(4));
			}
			
			ptsm = conn.getConnection().prepareStatement(sql2);
			ptsm.setInt(1, id);
			rs = ptsm.executeQuery();
			
			while(rs.next()) {
				exames.add(new Exame(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			
			p.setExames(exames);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			conn.closeConnection();
		}
		return p;
	}

	@Override
	public List<Paciente> findAll() {
		
		List<Paciente> listaPac = new ArrayList<>();
		List<Exame> listaExa = new ArrayList<>();
		String sql = "SELECT * FROM javaproject.PACIENTE;";
		String sql2 = "SELECT * FROM javaproject.EXAME;";
		PreparedStatement pstm;;
		ResultSet rs;
		Paciente p;
		Exame ex;
		
		try {
			pstm = conn.getConnection().prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				p = new Paciente(rs.getInt("id"), rs.getString("nome"), rs.getFloat("altura"), rs.getFloat("peso"));
				listaPac.add(p);
			}
			
			rs = null;
			pstm = conn.getConnection().prepareStatement(sql2);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				ex = new Exame(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("idpaciente"));
				listaExa.add(ex);
			}
			
			// OPCAO UTILIZANDO JAVA STREAM API
			listaPac.forEach(pac -> pac.setExames(
                    listaExa.stream()
                            .filter(exa -> exa.getIdPaciente() == pac.getId())
                            .collect(Collectors.toList())
                    )
            );
			
			//OPCAO UTILIZANDO DOIS FORS
//			for(Paciente pac : listaPac) {
//				for(Exame exa : listaExa) {
//					if(pac.getId() == exa.getIdPaciente()) {
//						pac.getExames().add(exa);
//					}
//				}
//			}
			return listaPac;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
