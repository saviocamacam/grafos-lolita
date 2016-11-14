package lolita;

public class Vertice {
	
	private int valor;
	
	public Vertice(int i) {
		this.setValor(i);
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.valor == ((Vertice) o).getValor();
	}

}
