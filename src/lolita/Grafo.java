package lolita;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Grafo {
	
	private Set<Aresta> conjuntoArestas;
	private Set<Vertice> conjuntoVertices;
	private Set<Rotulo> conjuntoRotulos;
	private int totalVertices;
	private int totalRotulos;
	private List<String> linhasBrutas;
	
	public Grafo(int totalVertices, int totalRotulos) {
		this.conjuntoArestas = new HashSet<>();
		this.conjuntoVertices = new HashSet<>();
		this.conjuntoRotulos = new HashSet<>();
		this.setTotalVertices(totalVertices);
		this.setTotalRotulos(totalRotulos);
	}
	
	public void paranaue() {
		int i, j;
		
		for(i=0 ; i < totalVertices ; i++) {
			LinkedList<Rotulo> rotulos = new LinkedList<>();
			String[] stringRotulos = linhasBrutas.get(i).split(" ");
			
			for(j=0 ; j < stringRotulos.length ; j++) {
				Rotulo rotulo = new Rotulo(stringRotulos[j]);
				conjuntoRotulos.add(rotulo);
			}
		}
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

	public int getTotalRotulos() {
		return totalRotulos;
	}

	public void setTotalRotulos(int totalRotulos) {
		this.totalRotulos = totalRotulos;
	}

	public int getTotalVertices() {
		return totalVertices;
	}

	public void setTotalVertices(int totalVertices) {
		this.totalVertices = totalVertices;
	}

	public List<String> getLinhasBrutas() {
		return linhasBrutas;
	}

	public void setLinhasBrutas(List<String> linhasBrutas) {
		this.linhasBrutas = linhasBrutas;
	}
}
