package lolita;

import java.util.HashSet;
import java.util.LinkedList;

public class SubGraph {
	
	private int incidenceMatrix[][];
	private int arestas;
	private int label;
	private HashSet<Integer> conjuntoVertices;
	private LinkedList<HashSet<Integer>> componentsSet;
	private HashSet<Aresta> edgeSet;
	private boolean consumed;
	private boolean contained;
	
	public SubGraph(int label, int incidenceMatrix[][]) {
		this.setIncidenceMatrix(incidenceMatrix);
		this.componentsSet = new LinkedList<>();
		this.setConjuntoVertices(new HashSet<>());
		this.label = label;
		this.setConsumed(false);
		this.setContained(false);
		this.setEdgeSet(new HashSet<Aresta>());
	}

	public LinkedList<HashSet<Integer>> getComponentsSet() {
		return componentsSet;
	}

	public int getComponents() {
		return componentsSet.size();
	}

	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(int incidenceMatrix[][]) {
		this.incidenceMatrix = incidenceMatrix;
	}

	public void setEdges(int arestas) {
		this.arestas = arestas;
	}

	public int getArestas() {
		return arestas;
	}
	
	public HashSet<Integer> getConjuntoVertices() {
		return conjuntoVertices;
	}
	
	public void setConjuntoVertices(HashSet<Integer> conjuntoVertices) {
		this.conjuntoVertices = conjuntoVertices;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public void setComponentsSet(LinkedList<HashSet<Integer>> listSet) {
		this.componentsSet = listSet;
	}

	public int getMaxComponent() {
		int maior = -99999999;
		for(HashSet<Integer> hs: componentsSet) {
			if(hs.size() > maior)
				maior = hs.size();
		}
		return maior;
	}

	public boolean isContained() {
		return contained;
	}

	public void setContained(boolean contained) {
		this.contained = contained;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}

	public HashSet<Aresta> getEdgeSet() {
		return edgeSet;
	}

	public void setEdgeSet(HashSet<Aresta> edgeSet) {
		this.edgeSet = edgeSet;
	}

}
