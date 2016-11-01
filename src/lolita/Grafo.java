package lolita;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Grafo {
	
	private LinkedList<Aresta> conjuntoArestas;
	private LinkedList<Vertice> conjuntoVertices;
	private LinkedList<Rotulo> conjuntoRotulos;
	private int totalVertices;
	private int totalRotulos;
	private List<String> linhasBrutas;
	
	public Grafo(int totalVertices, int totalRotulos) {
		this.conjuntoArestas = new LinkedList<Aresta>();
		this.conjuntoVertices = new LinkedList<Vertice>();
		this.conjuntoRotulos = new LinkedList<Rotulo>();
		this.setTotalVertices(totalVertices);
		this.setTotalRotulos(totalRotulos);
	}
	
	public void paranaue() {
		int i, j;
		
		for(i=0 ; i < totalVertices ; i++) {
			LinkedList<Rotulo> rotulos = new LinkedList<>();
			String[] stringRotulos = linhasBrutas.get(i).split(" ");
			Vertice verticeI = new Vertice(i+1);
			
			if(!conjuntoVertices.contains(verticeI)) {
				conjuntoVertices.add(verticeI);
			}
			
			for(j=0 ; j < stringRotulos.length ; j++) {
				Vertice verticeJ = new Vertice(j);
				Rotulo rotulo = new Rotulo(stringRotulos[j]);
				
				if(!conjuntoRotulos.contains(rotulo))
					conjuntoRotulos.add(rotulo);
				
				if(!conjuntoVertices.contains(verticeJ)) {
					conjuntoVertices.add(verticeJ);
				}
				
				if(rotulo.getValor() != this.totalRotulos) {
					Aresta aresta = new Aresta(rotulo, verticeI, verticeJ);
					conjuntoArestas.add(aresta);
				}
			}
		}
	}

	public LinkedList<Aresta> getConjuntoArestas() {
		return conjuntoArestas;
	}

	public void setConjuntoArestas(LinkedList<Aresta> conjuntoArestas) {
		this.conjuntoArestas = conjuntoArestas;
	}

	public LinkedList<Vertice> getConjuntoVertices() {
		return conjuntoVertices;
	}

	public void setConjuntoVertices(LinkedList<Vertice> conjuntoVertices) {
		this.conjuntoVertices = conjuntoVertices;
	}

	public LinkedList<Rotulo> getConjuntoRotulos() {
		return conjuntoRotulos;
	}

	public void setConjuntoRotulos(LinkedList<Rotulo> conjuntoRotulos) {
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
