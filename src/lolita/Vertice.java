package lolita;

import java.util.LinkedList;

public class Vertice {
	
	private int valor;
	private LinkedList<Aresta> conjuntoLocalAresta;
	
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

	public LinkedList<Aresta> getConjuntoLocalAresta() {
		return conjuntoLocalAresta;
	}

	public void setConjuntoLocalAresta(LinkedList<Aresta> conjuntoLocalAresta) {
		this.conjuntoLocalAresta = conjuntoLocalAresta;
	}

}
