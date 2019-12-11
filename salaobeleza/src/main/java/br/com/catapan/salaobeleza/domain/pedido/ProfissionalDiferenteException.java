package br.com.catapan.salaobeleza.domain.pedido;

@SuppressWarnings("serial")
public class ProfissionalDiferenteException extends Exception {

	public ProfissionalDiferenteException() {
		super();
	}

	public ProfissionalDiferenteException(String message) {
		super(message);
	}
}
