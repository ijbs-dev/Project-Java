package org.exemplo.persistencia.database.dao;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.time.Instant;
import java.time.LocalDateTime;
//import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;
//import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.RegistroTransacao;
import org.exemplo.persistencia.database.model.TipoTransacao;

public class ContaBancariaDAO implements IEntityContaDAO<ContaBancaria> {

	private IConnection conn;
	private boolean statusConta;
	//private IEntityDAO<Cliente> clienteDAO;
	
	public ContaBancariaDAO(IConnection conn) {
		this.conn = conn;
		//clienteDAO = new ClienteDAO(conn);
	}

	@Override
	public void save(ContaBancaria t) {
	    String sql = "INSERT INTO JAVAPROJECT.ContaBancaria VALUES (?,?,?,?,?,?);";

	    try {
	        PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
	        ptsm.setInt(1, t.getIdConta());
	        ptsm.setInt(2, t.getNumeroConta());
	        ptsm.setFloat(3, t.getSaldoConta());

	        LocalDateTime localDateTime = t.getDataAbertConta();
	        if (localDateTime != null) {
	            java.sql.Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());
	            ptsm.setDate(4, sqlDate);
	        } else {
	            ptsm.setNull(4, java.sql.Types.DATE);
	        }

	        ptsm.setBoolean(5, t.isStatusConta());
	        ptsm.setInt(6, t.getIdCliente());
	        ptsm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	@Override
	public ContaBancaria findById(Integer id) {
	    String sql = "SELECT * FROM ContaBancaria WHERE IDCONTA = ?;";
	    String sql2 = "SELECT * FROM RegistroTransacao WHERE idConta = ?;";
	    ContaBancaria conta = null;
	    ResultSet rs1, rs2;

	    try {
	        PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	        pstm.setInt(1, id);
	        rs1 = pstm.executeQuery();

	        if (rs1.next()) {
	            conta = new ContaBancaria(rs1.getInt("idConta"), rs1.getInt("idCliente"), null, 0.0f);
	            System.out.println(conta);
	            PreparedStatement pstm2 = conn.getConnection().prepareStatement(sql2);
	            pstm2.setInt(1, id);
	            rs2 = pstm2.executeQuery();

	            List<RegistroTransacao> transacoes = new ArrayList<>();
	            while (rs2.next()) {
	                RegistroTransacao transacao = new RegistroTransacao(null, sql2, null, id);
	                transacao.setIdRegistro(rs2.getInt("idRegistro"));
	                transacao.setValorRegistro(rs2.getFloat("valorRegistro"));
	                transacao.setTipoRegistro(rs2.getString("tipoRegistro"));
	                transacao.setDataRegistro(rs2.getTimestamp("dataRegistro").toLocalDateTime());
	                transacao.setIdConta(rs2.getInt("idConta"));
	                System.out.println(transacao);
	                transacoes.add(transacao);
	            }

	            conta.setTransacoes(transacoes);
	            return conta;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	@Override
	public void update(ContaBancaria t) {
	    // Verificar se a conta já existe no banco de dados
	    ContaBancaria temp = findById(t.getIdConta());

	    if (temp != null) {
	        // Atualizar os atributos da conta existente
	        temp.setIdConta(t.getIdConta());
	        temp.setNumeroConta(t.getNumeroConta());
	        temp.setSaldoConta(t.getSaldoConta());
	        temp.setDataAbertConta(t.getDataAbertConta());
	        temp.setStatusConta(t.isStatusConta());
	        temp.setIdCliente(t.getIdCliente());
	    } else {
	        // A conta não existe no banco de dados, atribuir o objeto t diretamente
	        temp = t;
	    }

	    String sql = "UPDATE ContaBancaria SET NUMEROCONTA = ?, SALDOCONTA = ?, DATAABERTCONTA = ?, STATUSCONTA = ? WHERE IDCONTA = ?;";

	    try {
	        PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	        pstm.setInt(1, t.getNumeroConta());
	        pstm.setFloat(2, t.getSaldoConta());
	        pstm.setTimestamp(3, Timestamp.valueOf(t.getDataAbertConta()));
	        pstm.setBoolean(4, t.isStatusConta());
	        pstm.setInt(5, t.getIdConta());
	        pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void delete(ContaBancaria t) {
		// TODO Auto-generated method stub
		
		  String deleteRegistro = "DELETE FROM javaproject.RegistroTransacao WHERE idConta = ?";
		    try (PreparedStatement statement = conn.getConnection().prepareStatement(deleteRegistro)) {
		        statement.setInt(1, t.getIdConta());
		        statement.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        
		    }
		    
		String sql = "delete from javaproject.ContaBancaria where idConta = ?;";

		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			pstm.setInt(1, t.getIdConta());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<ContaBancaria> findAll() {
		List<ContaBancaria> lista = new ArrayList<>();
		String sql = "SELECT * FROM JAVAPROJECT.ContaBancaria;";
		ResultSet rs;
		
		try {
			PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				lista.add(new ContaBancaria(rs.getInt("idCliente"), rs.getInt("idConta"), null, 0));
			}
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void depositar(ContaBancaria t, float valor) {
	    // Verificar se a conta já existe no banco de dados
	    ContaBancaria temp = findById(t.getIdConta());

	    if (temp != null) {
	        if (temp.isStatusConta()) {
	            if (valor > 0) {
	            	float novoSaldo = temp.getSaldoConta() + valor;
	                temp.setSaldoConta(novoSaldo);
//	                System.out.println(novoSaldo);

	                // Adicionar uma transação de depósito à conta
	                temp.getTransacoes().add(new RegistroTransacao(valor, TipoTransacao.CREDITO, LocalDateTime.now()));
	                System.out.println("Depósito realizado com sucesso.");

	                // Atualizar o saldo da conta no banco de dados
	                String sql = "UPDATE ContaBancaria SET SALDOCONTA = SALDOCONTA + ? WHERE IDCONTA = ?;";

	                try {
	                    PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	                    pstm.setFloat(1, novoSaldo);
	                    pstm.setInt(2, temp.getIdConta());
	                    pstm.executeUpdate();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                System.err.println("Valor inválido para depósito.");
	            }
	        } else {
	            System.err.println("Operação não permitida. Conta desativada.");
	        }
	    } else {
	        System.err.println("Conta não encontrada.");
	    }
	}

	
	public void sacar(ContaBancaria t, float valor) {
		 ContaBancaria temp = findById(t.getIdConta());
		 
		if (temp != null) {
			if (temp.isStatusConta()) {
				if (valor > 0) {
					temp.setSaldoConta(valor);					
		            if (temp.getSaldoConta() >= valor) {
		            	
		            	temp.setSaldoConta(0);		            	
		            	float novo = valor - temp.getSaldoConta();		            	
		            	temp.setSaldoConta(novo);
		            	
		                temp.getTransacoes().add(new RegistroTransacao(valor, TipoTransacao.DEBITO, LocalDateTime.now()));
		                System.out.println("Saque realizado com sucesso!");
		               
		                String sql = "UPDATE ContaBancaria SET SALDOCONTA = SALDOCONTA - ? WHERE IDCONTA = ?;";

		                try {
		                    PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
		                    pstm.setFloat(1, novo);
		                    pstm.setInt(2, temp.getIdConta());
		                    pstm.executeUpdate();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
			}
			
	            } else {
	                System.err.println("Saldo insuficiente.");
	            }
	        } else {
	            System.err.println("Valor inválido para saque.");
	        }
	    } else {
	        System.err.println("Operação não permitida. Conta desativada.");
	    }
	}

	public void transferir(ContaBancaria contaOrigem, ContaBancaria contaDestino, float valor) {
	    //ContaBancaria origem = findById(contaDestino.getIdConta());

	    if (contaOrigem != null && contaOrigem.isStatusConta()) {
	        if (contaDestino.isStatusConta()) {
	            if (valor > 0) {
	                System.out.println(contaOrigem.getSaldoConta());
	                contaOrigem.setSaldoConta(valor);
	                float novoSaldoOrigem = contaOrigem.getSaldoConta() - valor;
	                float novoSaldoDestino = contaDestino.getSaldoConta() + valor;
	                
	                if (novoSaldoOrigem >= 0) {
	                	contaOrigem.setSaldoConta(0);
	                    contaOrigem.setSaldoConta(novoSaldoOrigem);
	                    contaDestino.setSaldoConta(novoSaldoDestino);
	                    
	                    System.out.println("novo = " + contaOrigem.getSaldoConta());
	                    System.out.println("SaldoDestino = " + novoSaldoDestino);

	                    contaOrigem.getTransacoes().add(new RegistroTransacao(valor, TipoTransacao.DEBITO, LocalDateTime.now()));
	                    contaDestino.getTransacoes().add(new RegistroTransacao(valor, TipoTransacao.TRANSACAO_CREDITO, LocalDateTime.now()));
	                    System.out.println("Transferência realizada com sucesso!");

	                    // Atualizar o saldo das contas no banco de dados
	                    String sqlOrigem = "UPDATE ContaBancaria SET SALDOCONTA = SALDOCONTA - ? WHERE IDCONTA = ?;";
	                    String sqlDestino = "UPDATE ContaBancaria SET SALDOCONTA = SALDOCONTA + ? WHERE IDCONTA = ?;";

	                    try {
	                        PreparedStatement pstmOrigem = conn.getConnection().prepareStatement(sqlOrigem);
	                        pstmOrigem.setFloat(1, valor);
	                        pstmOrigem.setInt(2, contaOrigem.getIdConta());
	                        pstmOrigem.executeUpdate();

	                        System.out.println("Valor Origem = " + novoSaldoOrigem);
	                        System.out.println("ID Origem = " + contaOrigem.getIdConta());

	                        PreparedStatement pstmDestino = conn.getConnection().prepareStatement(sqlDestino);
	                        pstmDestino.setFloat(1, valor);
	                        pstmDestino.setInt(2, contaDestino.getIdConta());
	                        pstmDestino.executeUpdate();

	                        System.out.println("\nValor Destino = " + novoSaldoDestino);
	                        System.out.println("ID Destino = " + contaDestino.getIdConta());

	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                    }
	                } else {
	                    System.err.println("Saldo insuficiente para realizar a transferência.");
	                }
	            } else {
	                System.err.println("Valor inválido para transferência.");
	            }
	        } else {
	            System.err.println("Conta destino inválida ou desativada.");
	        }
	    } else {
	        System.err.println("Conta origem inválida ou desativada.");
	    }
	}
	
	public void imprimirExtratoConta(ContaBancaria cont, int mes, int ano) {
	    System.out.println("Extrato da Conta Bancária:");
	    System.out.println("ID Conta: " + cont.getIdConta());
	    System.out.println("Número da Conta: " + cont.getNumeroConta());
	    System.out.println("Saldo da Conta: " + cont.getSaldoConta());
	    System.out.println("Data de Abertura da Conta: " + cont.getDataAbertConta());
	    System.out.println("Status da Conta: " + (cont.isStatusConta() ? "Ativa" : "Inativa"));

	    // Consultar o banco de dados para obter as transações do extrato
	    String sql = "SELECT * FROM RegistroTransacao WHERE IDCONTA = ? AND EXTRACT(MONTH FROM DATAREGISTRO) = ? AND EXTRACT(YEAR FROM DATAREGISTRO) = ?";

	    try (Connection connection = getConnection()) {
	        if (connection != null) {
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, cont.getIdConta());
	            pstmt.setInt(2, mes);
	            pstmt.setInt(3, ano);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                float valor = rs.getFloat("VALOR");
	                TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("TIPO"));
	                LocalDateTime dataRegistro = rs.getTimestamp("DATA_REGISTRO").toLocalDateTime();

	                RegistroTransacao transacao = new RegistroTransacao(valor, tipo, dataRegistro);
	                System.out.println(transacao);
	            }

	            rs.close();
	            pstmt.close();
	        } else {
	            System.out.println("Falha ao obter conexão com o banco de dados.");
	        }
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	}

	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContaBancaria> findByIdCliente(Cliente clienteRemovido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContaBancaria findById(Cliente clienteContas) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
