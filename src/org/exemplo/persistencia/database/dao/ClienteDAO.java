package org.exemplo.persistencia.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.ContaBancaria;
import org.exemplo.persistencia.database.model.Cliente;

public class ClienteDAO implements IEntityDAO<Cliente>{

    private IConnection conn;
    private IEntityContaDAO<ContaBancaria> contaDAO;
    
    public ClienteDAO(IConnection conn) {
        this.conn = conn;
        contaDAO = new ContaBancariaDAO(conn);
    }
    
    public void save(Cliente cliente) {
        String sql = "INSERT INTO javaproject.Cliente (IDCLIENTE, NOMECLIENTE, CPFCLIENTE) VALUES (?,?,?);";
        
        try {
            PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
            ptsm.setInt(1, cliente.getIdCliente());
            ptsm.setString(2, cliente.getNomeCliente());
            ptsm.setString(3, cliente.getCpfCliente());            
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        for(ContaBancaria conta : cliente.getContas()) {
            contaDAO.save(conta);
        }
        
        conn.closeConnection();
    }
    
    public void delete(Cliente cliente) {
        // Primeiro, excluímos os registros relacionados na tabela contabancaria
        String deleteConta = "DELETE FROM javaproject.contabancaria WHERE idCliente = ?";
        try (PreparedStatement statement = conn.getConnection().prepareStatement(deleteConta)) {
            statement.setInt(1, cliente.getIdCliente());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Em seguida, excluímos o cliente
        String deleteCliente = "DELETE FROM javaproject.cliente WHERE idCliente = ?";
        try (PreparedStatement statement = conn.getConnection().prepareStatement(deleteCliente)) {
            statement.setInt(1, cliente.getIdCliente());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void update(Cliente cliente) {
        String sql = "UPDATE Cliente SET NOMECLIENTE = ?, CPFCLIENTE = ? WHERE IDCLIENTE = ?;";
        try {
            PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
            pstm.setString(1, cliente.getNomeCliente());
            pstm.setString(2, cliente.getCpfCliente());
            pstm.setInt(3, cliente.getIdCliente());
            pstm.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        for(ContaBancaria conta : cliente.getContas()) {
            if(contaDAO.findById(conta.getIdConta()) != null)
                contaDAO.update(conta);
            else
                contaDAO.save(conta);
        }
    }
   
    public Cliente findById(int idCliente) {
        String sql1 = "SELECT * FROM CLIENTE WHERE IDCLIENTE = ?;";
        String sql2 = "SELECT * FROM CONTABANCARIA WHERE IDCliente = ?;";
        ResultSet rs1, rs2;
        Cliente cliente = null;

        try {
            PreparedStatement ptsm1 = conn.getConnection().prepareStatement(sql1);
            ptsm1.setInt(1, idCliente);
            rs1 = ptsm1.executeQuery();

            if (rs1.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs1.getInt("idCliente"));
                cliente.setNomeCliente(rs1.getString("nomeCliente"));
                cliente.setCpfCliente(rs1.getString("cpfCliente"));
                //System.out.println(cliente);
                
                PreparedStatement ptsm2 = conn.getConnection().prepareStatement(sql2);
                ptsm2.setInt(1, idCliente);
                rs2 = ptsm2.executeQuery();

                List<ContaBancaria> contas = new ArrayList<>();
                while (rs2.next()) {
                    ContaBancaria conta = new ContaBancaria();
                    conta.setIdConta(rs2.getInt("idConta"));
                    conta.setNumeroConta(rs2.getInt("numeroConta"));
                    conta.setSaldoConta(rs2.getFloat("saldoConta"));        
                    conta.setDataAbertConta(rs2.getTimestamp("dataAbertConta").toLocalDateTime());
                    conta.setStatusConta(rs2.getBoolean("statusConta"));
                    conta.setIdCliente(rs2.getInt("idCliente"));
                    
                    contas.add(conta);
                } //System.out.println(contas);

                cliente.setContas(contas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }

        return cliente;
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM javaproject.CLIENTE;";
        String sql2 = "SELECT * FROM javaproject.CONTABANCARIA;";
        PreparedStatement pstm;
        ResultSet rs;
        Cliente cliente;
        ContaBancaria conta;

        try {
            pstm = conn.getConnection().prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                cliente = new Cliente(rs.getInt("idCliente"), rs.getString("cpfCliente"),rs.getString("nomeCliente") );
                listaClientes.add(cliente);
            }

            rs = null;
            pstm = conn.getConnection().prepareStatement(sql2);
            rs = pstm.executeQuery();

            while (rs.next()) {
                conta = new ContaBancaria(rs.getInt("idConta"), rs.getInt("idCliente"),null, 0);
                // Defina o valor para os demais atributos de ContaBancaria
                conta.setNumeroConta(rs.getInt("numeroConta"));
                conta.setSaldoConta(rs.getFloat("saldoConta"));
                conta.setDataAbertConta(rs.getTimestamp("dataAbertConta").toLocalDateTime());
                conta.setStatusConta(rs.getBoolean("statusConta"));

                for (Cliente cliente1 : listaClientes) {
                    if (cliente1.getIdCliente() == conta.getIdCliente()) {
                        cliente1.getContas().add(conta);
                        break;
                    }
                }
            }

            return listaClientes;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }

        return null;
    }
    
	/*
	 * public ContaBancaria localizarContaNumero(int numero) { for (Cliente cliente
	 * : findAll()) { for (ContaBancaria conta : cliente.getContas()) { if
	 * (conta.getNumeroConta() == numero) { System.out.println("Conta encontrada!");
	 * return conta; } } } System.out.println("Conta não encontrada."); return null;
	 * }
	 */

    public double balancoEntreContas() {
        double valorSaldo = 0.0;
        for (Cliente cliente : findAll()) {
            for (ContaBancaria conta : cliente.getContas()) {
                valorSaldo += conta.getSaldoConta();
                System.out.println("Balanco entre contas: RS " + valorSaldo);
            }
        }        
        return valorSaldo;
    }

	

	

//	@Override
//	public Cliente findByCpf(String cpf) {
//		String sql1 = "SELECT * FROM CLIENTE WHERE IDCLIENTE = ?;";
//        String sql2 = "SELECT * FROM CONTABANCARIA WHERE IDCliente = ?;";
//        ResultSet rs1, rs2;
//        Cliente cliente = null;
//
//        try {
//            PreparedStatement ptsm1 = conn.getConnection().prepareStatement(sql1);
//            ptsm1.setString(1, cpf);
//            rs1 = ptsm1.executeQuery();
//
//            if (rs1.next()) {
//                cliente = new Cliente();
//                cliente.setIdCliente(rs1.getInt("idCliente"));
//                cliente.setNomeCliente(rs1.getString("nomeCliente"));
//                cliente.setCpfCliente(rs1.getString("cpfCliente"));
//                System.out.println(cliente);
//                
//                PreparedStatement ptsm2 = conn.getConnection().prepareStatement(sql2);
//                ptsm2.setString(1, cpf);
//                rs2 = ptsm2.executeQuery();
//
//                List<ContaBancaria> contas = new ArrayList<>();
//                while (rs2.next()) {
//                    ContaBancaria conta = new ContaBancaria();
//                    conta.setIdConta(rs2.getInt("idConta"));
//                    conta.setNumeroConta(rs2.getInt("numeroConta"));
//                    conta.setSaldoConta(rs2.getFloat("saldoConta"));        
//                    conta.setDataAbertConta(rs2.getTimestamp("dataAbertConta").toLocalDateTime());
//                    conta.setStatusConta(rs2.getBoolean("statusConta"));
//                    conta.setIdCliente(rs2.getInt("idCliente"));
//                    
//                    contas.add(conta);
//                } System.out.println(contas);
//
//                cliente.setContas(contas);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            conn.closeConnection();
//        }
//
//        return cliente;
//	}
//
//	@Override
//	public Cliente findById(int idCliente) {
//		// TODO Auto-generated method stub
//		return null;
//	}



}

                
