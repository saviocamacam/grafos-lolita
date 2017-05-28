package lolita;

import java.util.LinkedList;

public class Vertice {
	
	private int valor;
	private int labelAdjacentFor;
	private LinkedList<Aresta> conjuntoLocalAresta;
	
	public Vertice(int i, int label) {
		this.setValor(i);
		this.setLabelAdjacentFor(label);
	}

	public Vertice(int j) {
		this.setValor(j);
	}

	private void setLabelAdjacentFor(int label) {
		this.labelAdjacentFor = label;
	}

	public int getValor() {
		return valor;
	}
	
	public int getLabelAdjacentFor() {
		return labelAdjacentFor;
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
