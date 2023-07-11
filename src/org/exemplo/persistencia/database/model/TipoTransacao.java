package org.exemplo.persistencia.database.model;

public enum TipoTransacao {

	CREDITO(1),
	DEBITO(2),
	TRANSACAO_CREDITO(3),
	TRANSACAO_DEBITO(4);
	
	private final int valor;
	
	private TipoTransacao(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static TipoTransacao transacaoFromValor(int valor) {
		for(TipoTransacao t : values()) {
			if(t.getValor() == valor)
				return t;
		}
		return null;
	}
	
}