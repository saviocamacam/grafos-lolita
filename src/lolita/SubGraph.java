package lolita;

import java.util.HashSet;
import java.util.LinkedList;

public class SubGraph {
	
	private int incidenceMatrix[][];
	private int components;
	private int arestas;
	private int label;
	private HashSet<Integer> conjuntoVertices;
	private LinkedList<HashSet<Integer>> componentsSet;
	
	public SubGraph(int label, int incidenceMatrix[][]) {
		this.setIncidenceMatrix(incidenceMatrix);
		this.componentsSet = new LinkedList<>();
		this.setConjuntoVertices(new HashSet<>());
		this.label = label;
	}

	public LinkedList<HashSet<Integer>> getComponentsSet() {
		return componentsSet;
	}

	public int getComponents() {
		return components;
	}

	public void setComponents(int components) {
		this.components = components;
	}

	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}

	public void setIncidenceMatrix(int incidenceMatrix[][]) {
		this.incidenceMatrix = incidenceMatrix;
	}

	public void setArestas(int arestas) {
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
		int maior = -999999;
		for(HashSet<Integer> hs: componentsSet) {
			if(hs.size() > maior)
				maior = hs.size();
		}
		return maior;
	}

}
