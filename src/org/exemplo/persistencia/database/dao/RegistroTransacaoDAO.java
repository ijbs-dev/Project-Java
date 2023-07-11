package org.exemplo.persistencia.database.dao;

//import java.math.BigDecimal;
//import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;
import org.exemplo.persistencia.database.model.RegistroTransacao;
import org.exemplo.persistencia.database.model.TipoTransacao;

public class RegistroTransacaoDAO implements IEntityRegistroDAO<RegistroTransacao> {

	private IConnection conn;

	public RegistroTransacaoDAO(IConnection conn) {
		this.conn = conn;
	}

	@Override
	public void save(RegistroTransacao t) {
		String sql = "INSERT INTO RegistroTransacao (valorRegistro, tipoRegistro, dataRegistro, idConta) VALUES (?,?,?,?);";

		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
			ptsm.setFloat(1, t.getValorRegistro());
			ptsm.setString(2, t.getTipoRegistro());
			
			LocalDateTime localDateTime = t.getDataRegistro();
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());
			ptsm.setDate(3, sqlDate);
			
			ptsm.setInt(4, t.getIdConta());
			ptsm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public RegistroTransacao findById(Integer id) {
	    String sql = "SELECT * FROM RegistroTransacao WHERE idRegistro = ?;";
	    ResultSet rs;

	    try {
	        PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	        pstm.setInt(1, id);
	        rs = pstm.executeQuery();

	        if (rs.next()) {
	        	
	        	RegistroTransacao registro = new RegistroTransacao(rs.getFloat("valorRegistro"),
	                    rs.getString("tipoRegistro"), rs.getTimestamp("dataRegistro").toLocalDateTime(), rs.getInt("idConta"));

          
	        	// Imprimir os valores
	            System.out.println("ID Registro: " + registro.getIdRegistro());
	            System.out.println("Valor Registro: " + registro.getValorRegistro());
	            System.out.println("Tipo Registro: " + registro.getTipoRegistro());
	            System.out.println("Data Registro: " + registro.getDataRegistro());
	            
	            return registro;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}




	@Override
	public void update(RegistroTransacao t) {
		String sql = "UPDATE RegistroTransacao SET valorRegistro = ?, tipoRegistro = ?, dataRegistro = ?, idConta = ? WHERE idRegistro = ?;";

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setFloat(1, t.getValorRegistro());
			pstm.setString(2, t.getTipoRegistro());
			
			// Converter LocalDateTime em java.sql.Date
			LocalDateTime localDateTime = t.getDataRegistro();
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());
			pstm.setDate(3, sqlDate);
						
			pstm.setInt(4, t.getIdConta());
			pstm.setInt(5, t.getIdRegistro());
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(RegistroTransacao t) {
		String sql = "DELETE FROM RegistroTransacao WHERE idRegistro = ?;";

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, t.getIdRegistro());
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<RegistroTransacao> findAll() {
	    List<RegistroTransacao> lista = new ArrayList<>();
	    String sql = "SELECT * FROM RegistroTransacao;";
	    ResultSet rs;

	    try {
	        PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            // Integer idRegistro, BigDecimal valorRegistro, String tipoRegistro, LocalDateTime dataRegistro, Integer idConta
	            java.sql.Timestamp timestamp = rs.getTimestamp("dataRegistro");
	            LocalDateTime dataRegistro = timestamp.toLocalDateTime();

	            RegistroTransacao registro = new RegistroTransacao(rs.getFloat("valorRegistro"),
	                    rs.getString("tipoRegistro"), rs.getTimestamp("dataRegistro").toLocalDateTime(), rs.getInt("idConta"));

	            lista.add(registro);
	        }
	        return lista;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	@Override
	public List<RegistroTransacao> getExtrato(ContaBancaria contaExtrato, int mesExtrato, int anoExtrato) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
