package lolita;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
	
	private Set<Aresta> conjuntoArestas;
	private Set<Vertice> conjuntoVertices;
	private Set<Rotulo> conjuntoRotulos;
	
	public Grafo() {
		this.conjuntoArestas = new HashSet<>();
		this.conjuntoVertices = new HashSet<>();
		this.conjuntoRotulos = new HashSet<>();
	}

	public Set<Aresta> getConjuntoArestas() {
		return conjuntoArestas;
	}

	public void setConjuntoArestas(Set<Aresta> conjuntoArestas) {
		this.conjuntoArestas = conjuntoArestas;
	}

	public Set<Vertice> getConjuntoVertices() {
		return conjuntoVertices;
	}

	public void setConjuntoVertices(Set<Vertice> conjuntoVertices) {
		this.conjuntoVertices = conjuntoVertices;
	}

	public Set<Rotulo> getConjuntoRotulos() {
		return conjuntoRotulos;
	}

	public void setConjuntoRotulos(Set<Rotulo> conjuntoRotulos) {
		this.conjuntoRotulos = conjuntoRotulos;
	}

}
