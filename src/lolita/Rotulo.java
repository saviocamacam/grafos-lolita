package lolita;

public class Rotulo {
	private int valor;
	
	public Rotulo(String stringRotulos) {
		this.setValor(Integer.valueOf(stringRotulos));
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
