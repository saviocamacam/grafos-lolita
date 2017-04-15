package lolita;

public class Rotulo {
	private int valor;
	
	public Rotulo(int labelValue) {
		this.setValor(labelValue);
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.valor == ((Rotulo) o).getValor();
	}

}
