package org.exemplo.persistencia.database.application;

import java.util.List;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.ContaBancariaDAO;
import org.exemplo.persistencia.database.dao.IEntityContaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.dao.IEntityRegistroDAO;
import org.exemplo.persistencia.database.dao.RegistroTransacaoDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoMySQL;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaBancaria;
import org.exemplo.persistencia.database.model.RegistroTransacao;

public class Application {

	public static void main(String[] args) {
		// Inicializa��o dos DAOs e Scanner
		IEntityDAO<Cliente> daoCli = new ClienteDAO(new ConexaoBancoMySQL());
		IEntityContaDAO<ContaBancaria> daoCont = new ContaBancariaDAO(new ConexaoBancoMySQL());
		IEntityRegistroDAO<RegistroTransacao> daoTran = new RegistroTransacaoDAO(new ConexaoBancoMySQL());

		while (true) {
			String opcao = JOptionPane.showInputDialog(null, "===> ESCOLHA UMA OPCAO: <===\n\n" 
					+ "1. Cadastrar novo cliente\n" 
					+ "2. Listar os clientes cadastrados\n"
					+ "3. Consultar cliente por ID\n" 
					+ "4. Remover cliente\n" 
					+ "5. Alterar dados cliente\n"
					+ "6. Criar conta e associar ao cliente\n"
					+ "7. Listar as contas cadastradas do cliente\n" 
					+ "8. Remover conta de um dado cliente\n"
					+ "9. Realizar deposito de uma dada quantia\n" 
					+ "10. Realizar saque de uma dada quantia\n"
					+ "11. Efetuar transferencia de quantia entre contas\n"
					+ "12. Imprimir extrato da movimentacao financeira com base no mes e ano\n"
					+ "13. Consultar saldo\n" 
					+ "14. Consultar balanco das contas");

			switch (opcao) {
			case "1":
				String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
				String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
				int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
				Cliente cliente = new Cliente(id, cpf, nome);
				daoCli.save(cliente);
				JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
				break;

			case "2":
				StringBuilder clientesCadastrados = new StringBuilder();
				clientesCadastrados.append("Clientes cadastrados:\n");
				for (Cliente c : daoCli.findAll()) {
					clientesCadastrados.append(c.toString()).append("\n");
				}
				JOptionPane.showMessageDialog(null, clientesCadastrados.toString());
				break;

			case "3":
				int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
				Cliente clienteConsultado = daoCli.findById(idCliente);
				if (clienteConsultado != null) {
					JOptionPane.showMessageDialog(null, clienteConsultado.toString());
				} else {
					JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
				}
				break;

			case "4":
			    int idClienteRem = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente a ser removido:"));
			    Cliente clienteRemovido = daoCli.findById(idClienteRem);
			    if (clienteRemovido != null) {
			        // Verificar se o cliente possui contas associadas
			        List<ContaBancaria> contasDoCliente = daoCont.findByIdCliente(clienteRemovido);
			        if (contasDoCliente != null && !contasDoCliente.isEmpty()) {
			            StringBuilder mensagemRemocao = new StringBuilder();
			            mensagemRemocao.append("O cliente possui as seguintes contas associadas:\n");
			            for (ContaBancaria conta : contasDoCliente) {
			                mensagemRemocao.append(conta.toString()).append("\n");
			            }
			            mensagemRemocao.append("Deseja remover o cliente e suas contas?");
			            int confirmacao = JOptionPane.showConfirmDialog(null, mensagemRemocao.toString(), "Confirmação",
			                    JOptionPane.YES_NO_OPTION);
			            if (confirmacao == JOptionPane.YES_OPTION) {
			                for (ContaBancaria conta : contasDoCliente) {
			                    daoCont.delete(conta);
			                }
			                daoCli.delete(clienteRemovido);
			                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
			            } else {
			                JOptionPane.showMessageDialog(null, "Remoção cancelada.");
			            }
			        } else {
			            daoCli.delete(clienteRemovido);
			            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			    }
			    break;



			    case "5":
			        int idClienteAlt = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente a ser alterado:"));
			        Cliente clienteAlterar = daoCli.findById(idClienteAlt);
			        if (clienteAlterar != null) {
			            String novoNome = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
			            clienteAlterar.setNomeCliente(novoNome);
			            daoCli.update(clienteAlterar);
			            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
			        } else {
			            JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
			        }
			        break;
			        
			    case "6":
			    	int idCliente1 = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
			    	Cliente cliente1 = daoCli.findById(idCliente1);
			    	if (cliente1 != null) {
			    	    int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta:"));			            
			    	    Cliente clienteAlterar1 = cliente1;
			    	    
			    	    ContaBancaria novaConta = new ContaBancaria(numeroConta, cliente1.getIdCliente(), null, 0);
			    	    clienteAlterar1.adicionarConta(novaConta);
						
			    	        daoCli.update(clienteAlterar1);
			    	        JOptionPane.showMessageDialog(null, "Conta criada com sucesso!");
			    	    } else {
			    	        JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			    	    }			    	
			    	break;


			    case "7":
			    	int idClienteCons = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente a ser consultado:"));
			    	Cliente clienteConsultar = daoCli.findById(idClienteCons);
			    	if (clienteConsultar != null) {
			    	    StringBuilder mensagemContas = new StringBuilder();
			    	    mensagemContas.append("Contas cadastradas do cliente:\n");
			    	    for (ContaBancaria conta : clienteConsultar.getContas()) {
			    	        mensagemContas.append(conta.toString()).append("\n");
			    	    }
			    	    JOptionPane.showMessageDialog(null, mensagemContas.toString());
			    	} else {
			    	    JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			    	}
			    	break;


			        
			    case "8":
			    
			        int idContaCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
			        cliente = daoCli.findById(idContaCliente);

			        if (cliente != null) {
			            int idConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta a ser removida:"));
			            ContaBancaria contaRemovida = cliente.localeById(idConta);
			            if (contaRemovida != null) {
			                cliente.removerConta(contaRemovida);
			                daoCli.delete(cliente);
			                JOptionPane.showMessageDialog(null, "Conta removida do cliente com sucesso!");
			            } else {
			                JOptionPane.showMessageDialog(null, "Conta não encontrada");
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			        }
			        break;


			        
			    case "9":
			    	int idCliente3 = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
			        Cliente cliente3 = daoCli.findById(idCliente3);

			        if (cliente3 != null) {
			            int idContaDeposito = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta para realizar o depósito:"));
			            ContaBancaria cliente4 = daoCont.findById(idContaDeposito);
			            
			            if (cliente4!= null) {
			            	
			                float valorDeposito = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do depósito:"));			               
			                
			                cliente4.depositar(valorDeposito);
			                daoCont.depositar(cliente4, valorDeposito);
			                JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!");
			            } else {
			                JOptionPane.showMessageDialog(null, "Conta não encontrada");
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			        }
			        break;
			        
			    case "10":
			    	int idCliente2 = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
			        Cliente cliente5 = daoCli.findById(idCliente2);

			        if (cliente5 != null) {
			            int idContaSaque = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta para realizar o saque:"));
			            ContaBancaria contaSaque = cliente5.localeById(idContaSaque);

			            if (contaSaque != null) {
			                float valorSaque = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do saque:"));

			                if (contaSaque.getSaldoConta() >= valorSaque) {
			                    contaSaque.sacar(valorSaque);
			                    daoCont.sacar(contaSaque, valorSaque);
			                    JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");
			                } else {
			                    JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar o saque");
			                }
			            } else {
			                JOptionPane.showMessageDialog(null, "Conta não encontrada");
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			        }
			        break;

			        
			    case "11":
			    	System.out.println("\nDigite o ID do cliente remetente:");
			    	int idRemetente = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente remetente:"));
			    	Cliente remetente = daoCli.findById(idRemetente);

			    	if (remetente != null) {
			    	    int idContaRemetente = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta remetente:"));
			    	    ContaBancaria contaRemetente = daoCont.findById(idContaRemetente);

			    	    if (contaRemetente != null) {
			    	        int idDestinatario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente destinatário:"));
			    	        Cliente destinatario = daoCli.findById(idDestinatario);

			    	        if (destinatario != null) {
			    	            int idContaDestinatario = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta destinatária:"));
			    	            ContaBancaria contaDestinatario = daoCont.findById(idContaDestinatario);

			    	            if (contaDestinatario != null) {
			    	                float valorTransferencia = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da transferência:"));

			    	                
			    	                    daoCont.transferir(contaRemetente, contaDestinatario, valorTransferencia);
			    	                    JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso!");
			    	                
			    	            } else {
			    	                JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência ou Conta destinatária não encontrada");
			    	            }
			    	        } else {
			    	            JOptionPane.showMessageDialog(null, "Cliente destinatário não encontrado");
			    	        }
			    	    } else {
			    	        JOptionPane.showMessageDialog(null, "Conta remetente não encontrada");
			    	    }
			    	} else {
			    	    JOptionPane.showMessageDialog(null, "Cliente remetente não encontrado");
			    	}
			    	break;


			    case "12":
			    	List<Cliente> clientes = daoCli.findAll();
			        StringBuilder mensagemClientes = new StringBuilder();
			        mensagemClientes.append("Lista de clientes:\n");

			        for (Cliente cliente11 : clientes) {
			            mensagemClientes.append(cliente11.toString()).append("\n");
			        }
			        JOptionPane.showMessageDialog(null, mensagemClientes.toString());
			        break;

			    default:
			        JOptionPane.showMessageDialog(null, "Opção inválida");
			        break;
			        
//			        int idCliente11 = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
//			        Cliente cli = daoCli.findById(idCliente11);
//
//			        if (cli != null) {
//			            int numeroContaExtrato = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta:"));
//			            ContaBancaria contaExtrato = daoCont.findById(numeroContaExtrato);
//
//			            if (contaExtrato != null) {
//			                int mesExtrato = Integer.parseInt(JOptionPane.showInputDialog("Digite o mês (número) do extrato:"));
//			                int anoExtrato = Integer.parseInt(JOptionPane.showInputDialog("Digite o ano do extrato:"));
//
//			                List<RegistroTransacao> extrato = daoTran.getExtrato(contaExtrato, mesExtrato, anoExtrato);
//
//			                if (extrato != null) {
//			                    JOptionPane.showMessageDialog(null, "Extrato de movimentação financeira:\n\n");
//			                    for (RegistroTransacao registro : extrato) {
//			                        JOptionPane.showMessageDialog(null, registro);
//			                    }
//			                } else {
//			                    JOptionPane.showMessageDialog(null, "Não há registros de transações para o período informado");
//			                }
//			            } else {
//			                JOptionPane.showMessageDialog(null, "Conta não encontrada");
//			            }
//			        } else {
//			            JOptionPane.showMessageDialog(null, "Cliente não encontrado");
//			        }
//			        break;



			    case "13":
			    	 int idClienteBalanco = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
			    	    Cliente clienteBalanco = daoCli.findById(idClienteBalanco);

			    	    if (clienteBalanco != null) {
			    	        float saldoTotal = 0.0f;
			    	        for (ContaBancaria conta : clienteBalanco.getContas()) {
			    	            saldoTotal += conta.getSaldoConta();
			    	        }
			    	        JOptionPane.showMessageDialog(null, "Saldo: R$" + saldoTotal);
			    	    } else {
			    	        JOptionPane.showMessageDialog(null, "Cliente não encontrado");
			    	    }
			    	    break;			    
			       

//			    case "14":
//			    	
//			    	int idClienteSaldo = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente:"));
//			    	Cliente cl = daoCli.findById(idClienteSaldo);
//
//			    	if (cl != null) {
//			    	    int numeroContaSaldo = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da conta:"));
//			    	    ContaBancaria contaSaldo = daoCont.findById(numeroContaSaldo);
//
//			    	    if (contaSaldo != null) {
//			    	        
//			    	        double valorSaldo = daoCli.balancoEntreContas(); // Chamada para calcular o balanço entre contas
//			    	        JOptionPane.showMessageDialog(null, "Balanco entre contas: RS " + valorSaldo);
//			    	    } else {
//			    	        JOptionPane.showMessageDialog(null, "Conta não encontrada");
//			    	    }
//			    	} else {
//			    	    JOptionPane.showMessageDialog(null, "Cliente não encontrado");
//			    	}
//			    	break;
			    case "14":
			    	double valorSaldo = daoCli.balancoEntreContas(); // Chamada para calcular o balanço entre contas
	    	        JOptionPane.showMessageDialog(null, "Balanco entre contas: RS " + valorSaldo);
	    	        break;
			}
		}
	}
  }


